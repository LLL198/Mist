package com.una.embyhub.pointsbot.service;

import com.alibaba.fastjson2.JSONObject;
import com.una.embyhub.pointsbot.model.BrainGameConfig;
import com.una.embyhub.pointsbot.model.BrainQuestion;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

@Service
public class BrainQuestionGenerator {
   public static final String TARGET_ARITHMETIC = "TARGET_ARITHMETIC";
   public static final String CODE_LOCK = "CODE_LOCK";
   public static final String BULLS_AND_COWS = "BULLS_AND_COWS";
   public static final String LIGHTS_OUT = "LIGHTS_OUT";
   public static final String FLASH_MEMORY = "FLASH_MEMORY";
   private static final Pattern INTEGER_PATTERN = Pattern.compile("\\d+");
   private final Random random = new SecureRandom();

   public BrainQuestion generate(BrainGameConfig config) {
      int total = config.getTargetArithmeticWeight()
         + config.getCodeLockWeight()
         + config.getBullsAndCowsWeight()
         + config.getLightsOutWeight()
         + config.getFlashMemoryWeight();
      if (total <= 0) {
         throw new IllegalArgumentException("没有启用 Brain 题型");
      } else {
         int draw = this.random.nextInt(total);
         if ((draw = draw - config.getTargetArithmeticWeight()) < 0) {
            return this.generateTargetArithmetic();
         } else if ((draw = draw - config.getCodeLockWeight()) < 0) {
            return this.generateCodeLock();
         } else if ((draw = draw - config.getBullsAndCowsWeight()) < 0) {
            return this.generateBullsAndCows();
         } else {
            return draw - config.getLightsOutWeight() < 0 ? this.generateLightsOut() : this.generateFlashMemory();
         }
      }
   }

   public BrainQuestionGenerator.AnswerCheck check(BrainQuestion question, String rawAnswer) {
      if (question != null && rawAnswer != null && !rawAnswer.isBlank()) {
         JSONObject data = JSONObject.parseObject(question.answerData());
         String var4 = question.type();

         return switch (var4) {
            case "TARGET_ARITHMETIC" -> this.checkTargetArithmetic(data, rawAnswer);
            case "CODE_LOCK", "FLASH_MEMORY" -> new BrainQuestionGenerator.AnswerCheck(data.getString("answer").equals(normalizeDigits(rawAnswer)), null);
            case "BULLS_AND_COWS" -> this.checkBullsAndCows(data.getString("answer"), rawAnswer);
            case "LIGHTS_OUT" -> this.checkLightsOut(data, rawAnswer);
            default -> new BrainQuestionGenerator.AnswerCheck(false, "题型不可用");
         };
      } else {
         return new BrainQuestionGenerator.AnswerCheck(false, "答案不能为空");
      }
   }

   private BrainQuestion generateTargetArithmetic() {
      for (int attempt = 0; attempt < 100; attempt++) {
         List<Integer> numbers = new ArrayList<>();

         for (int i = 0; i < 5; i++) {
            numbers.add(2 + this.random.nextInt(14));
         }

         long value = (long)numbers.get(0).intValue();
         StringBuilder example = new StringBuilder(String.valueOf(numbers.get(0)));

         for (int i = 1; i < numbers.size(); i++) {
            int next = numbers.get(i);

            char op = (char)(switch (this.random.nextInt(3)) {
               case 0 -> 43;
               case 1 -> 45;
               default -> 42;
            });

            value = switch (op) {
               case '+' -> value + (long)next;
               case '-' -> value - (long)next;
               default -> value * (long)next;
            };
            example.insert(0, "(").append(op).append(next).append(")");
         }

         if (value >= 10L && value <= 500L) {
            JSONObject answer = new JSONObject();
            answer.put("target", Long.valueOf(value));
            answer.put("numbers", numbers);
            String numberText = String.join("、", numbers.stream().map(String::valueOf).toList());
            return new BrainQuestion(
               "TARGET_ARITHMETIC",
               "\ud83e\uddee 目标数运算\n使用下面 5 个数字各一次，只允许 +、-、× 和括号，算出目标数。\n数字：" + numberText + "\n目标：" + value,
               null,
               answer.toJSONString(),
               "一种可行表达式：" + example,
               0
            );
         }
      }

      throw new IllegalStateException("目标数题生成失败");
   }

   private BrainQuestionGenerator.AnswerCheck checkTargetArithmetic(JSONObject data, String rawAnswer) {
      String expression = rawAnswer.replace('×', '*').replace('－', '-').replace(" ", "");
      if (expression.matches("[0-9+\\-*()]+") && expression.length() <= 100) {
         List<Integer> supplied = new ArrayList<>();
         Matcher matcher = INTEGER_PATTERN.matcher(expression);

         while (matcher.find()) {
            supplied.add(Integer.parseInt(matcher.group()));
         }

         List<Integer> expected = data.getList("numbers", Integer.class);
         supplied.sort(Comparator.naturalOrder());
         List<Integer> var10 = new ArrayList<>(expected);
         var10.sort(Comparator.naturalOrder());
         if (!supplied.equals(var10)) {
            return new BrainQuestionGenerator.AnswerCheck(false, "必须把给出的 5 个数字各使用一次");
         } else {
            try {
               long value = new BrainQuestionGenerator.IntegerExpressionParser(expression).parse();
               return new BrainQuestionGenerator.AnswerCheck(value == data.getLongValue("target"), null);
            } catch (RuntimeException var9) {
               return new BrainQuestionGenerator.AnswerCheck(false, "表达式格式不正确");
            }
         }
      } else {
         return new BrainQuestionGenerator.AnswerCheck(false, "只允许数字、+、-、× 和括号");
      }
   }

   private BrainQuestion generateCodeLock() {
      List<String> candidates = allCodes();
      String secret = candidates.get(this.random.nextInt(candidates.size()));
      List<BrainQuestionGenerator.Clue> clues = new ArrayList<>();
      List<String> remaining = new ArrayList<>(candidates);

      for (int attempt = 0; attempt < 30 && remaining.size() > 1; attempt++) {
         String guess = candidates.get(this.random.nextInt(candidates.size()));
         BrainQuestionGenerator.Clue clue = score(secret, guess);
         if (clue.a() != 4 && !clues.stream().anyMatch(item -> item.guess().equals(guess))) {
            List<String> filtered = remaining.stream().filter(candidate -> sameScore(score(candidate, guess), clue)).toList();
            if (filtered.size() < remaining.size()) {
               clues.add(new BrainQuestionGenerator.Clue(guess, clue.a(), clue.b()));
               remaining = new ArrayList<>(filtered);
            }
         }
      }

      if (remaining.size() != 1) {
         return this.generateCodeLock();
      } else {
         JSONObject answer = new JSONObject();
         answer.put("answer", secret);
         StringBuilder prompt = new StringBuilder("\ud83d\udd10 数字密码锁\n密码由 4 个不重复数字组成。A=数字和位置都对，B=数字对但位置错。\n");

         for (BrainQuestionGenerator.Clue clue : clues) {
            prompt.append(clue.guess()).append(" → ").append(clue.a()).append("A").append(clue.b()).append("B\n");
         }

         prompt.append("请直接回复 4 位密码。");
         return new BrainQuestion("CODE_LOCK", prompt.toString(), null, answer.toJSONString(), "唯一密码：" + secret, 0);
      }
   }

   private BrainQuestion generateBullsAndCows() {
      List<String> codes = allCodes();
      String secret = codes.get(this.random.nextInt(codes.size()));
      JSONObject answer = new JSONObject();
      answer.put("answer", secret);
      return new BrainQuestion(
         "BULLS_AND_COWS",
         "\ud83d\udc02 AB 猜数\n机器人已生成一个 4 位不重复数字。私聊提交猜测后会返回 A/B 提示，最多 8 次；A=数字和位置都对，B=数字对但位置错。",
         null,
         answer.toJSONString(),
         "答案：" + secret,
         0
      );
   }

   private BrainQuestionGenerator.AnswerCheck checkBullsAndCows(String secret, String rawAnswer) {
      String guess = normalizeDigits(rawAnswer);
      if (guess.length() == 4 && guess.chars().distinct().count() == 4L) {
         BrainQuestionGenerator.Clue clue = score(secret, guess);
         return new BrainQuestionGenerator.AnswerCheck(clue.a() == 4, clue.a() + "A" + clue.b() + "B");
      } else {
         return new BrainQuestionGenerator.AnswerCheck(false, "请输入 4 个不重复数字");
      }
   }

   private BrainQuestion generateLightsOut() {
      int board = 0;
      int presses = 1 + this.random.nextInt(511);

      for (int cell = 0; cell < 9; cell++) {
         if ((presses & 1 << cell) != 0) {
            board ^= pressMask(cell);
         }
      }

      int minimum = minimumLightsOutPresses(board);
      if (minimum <= 0) {
         return this.generateLightsOut();
      } else {
         JSONObject answer = new JSONObject();
         answer.put("answer", Integer.valueOf(minimum));
         return new BrainQuestion(
            "LIGHTS_OUT",
            "\ud83d\udca1 熄灯谜题\n点击一个格子会翻转它自己和上下左右。下面 \ud83d\udfe7 为亮、⬛ 为灭，把全部灯关掉最少需要点击几次？\n" + renderBoard(board) + "\n请只回复最少次数。",
            null,
            answer.toJSONString(),
            "最少需要 " + minimum + " 次。",
            0
         );
      }
   }

   private BrainQuestionGenerator.AnswerCheck checkLightsOut(JSONObject data, String rawAnswer) {
      try {
         int answer = Integer.parseInt(rawAnswer.trim());
         return new BrainQuestionGenerator.AnswerCheck(answer == data.getIntValue("answer"), null);
      } catch (NumberFormatException var4) {
         return new BrainQuestionGenerator.AnswerCheck(false, "请只回复一个整数");
      }
   }

   private BrainQuestion generateFlashMemory() {
      int[][] grid = new int[4][4];

      for (int row = 0; row < 4; row++) {
         for (int col = 0; col < 4; col++) {
            grid[row][col] = this.random.nextInt(10);
         }
      }

      Set<Integer> picked = new LinkedHashSet<>();

      while (picked.size() < 3) {
         picked.add(this.random.nextInt(16));
      }

      List<Integer> positions = new ArrayList<>(picked);
      StringBuilder expected = new StringBuilder();
      List<String> labels = new ArrayList<>();

      for (int position : positions) {
         int row = position / 4;
         int col = position % 4;
         expected.append(grid[row][col]);
         labels.add("第" + (row + 1) + "行第" + (col + 1) + "列");
      }

      JSONObject answer = new JSONObject();
      answer.put("answer", expected.toString());
      String question = "⚡ 闪记挑战\n请记住 4×4 数字矩阵，5 秒后题面会隐藏。\n" + renderGrid(grid) + "\n按顺序回答：" + String.join("、", labels);
      String hidden = "⚡ 闪记挑战\n矩阵已隐藏。请按顺序回复 " + String.join("、", labels) + " 的数字，连成 3 位数。";
      return new BrainQuestion("FLASH_MEMORY", question, hidden, answer.toJSONString(), "正确答案：" + expected, 5);
   }

   private static String renderGrid(int[][] grid) {
      StringBuilder result = new StringBuilder();

      for (int[] row : grid) {
         for (int value : row) {
            result.append('`').append(value).append('`').append(' ');
         }

         result.append('\n');
      }

      return result.toString().trim();
   }

   private static String renderBoard(int board) {
      StringBuilder result = new StringBuilder();

      for (int cell = 0; cell < 9; cell++) {
         result.append((board & 1 << cell) != 0 ? "\ud83d\udfe7" : "⬛");
         if (cell % 3 == 2 && cell < 8) {
            result.append('\n');
         }
      }

      return result.toString();
   }

   private static int minimumLightsOutPresses(int board) {
      int minimum = Integer.MAX_VALUE;

      for (int presses = 0; presses < 512; presses++) {
         int state = board;

         for (int cell = 0; cell < 9; cell++) {
            if ((presses & 1 << cell) != 0) {
               state ^= pressMask(cell);
            }
         }

         if (state == 0) {
            minimum = Math.min(minimum, Integer.bitCount(presses));
         }
      }

      return minimum;
   }

   private static int pressMask(int cell) {
      int row = cell / 3;
      int col = cell % 3;
      int mask = 1 << cell;
      if (row > 0) {
         mask |= 1 << cell - 3;
      }

      if (row < 2) {
         mask |= 1 << cell + 3;
      }

      if (col > 0) {
         mask |= 1 << cell - 1;
      }

      if (col < 2) {
         mask |= 1 << cell + 1;
      }

      return mask;
   }

   private static List<String> allCodes() {
      List<String> result = new ArrayList<>(5040);

      for (int a = 0; a < 10; a++) {
         for (int b = 0; b < 10; b++) {
            for (int c = 0; c < 10; c++) {
               for (int d = 0; d < 10; d++) {
                  if (a != b && a != c && a != d && b != c && b != d && c != d) {
                     result.add("" + a + b + c + d);
                  }
               }
            }
         }
      }

      return result;
   }

   private static BrainQuestionGenerator.Clue score(String secret, String guess) {
      int a = 0;
      int b = 0;

      for (int i = 0; i < 4; i++) {
         if (secret.charAt(i) == guess.charAt(i)) {
            a++;
         } else if (secret.indexOf(guess.charAt(i)) >= 0) {
            b++;
         }
      }

      return new BrainQuestionGenerator.Clue(guess, a, b);
   }

   private static boolean sameScore(BrainQuestionGenerator.Clue left, BrainQuestionGenerator.Clue right) {
      return left.a() == right.a() && left.b() == right.b();
   }

   private static String normalizeDigits(String answer) {
      return answer == null ? "" : answer.replaceAll("\\s+", "");
   }

   public static record AnswerCheck(boolean correct, String feedback) {
   }

   private static record Clue(String guess, int a, int b) {
   }

   private static final class IntegerExpressionParser {
      private final String input;
      private int index;

      private IntegerExpressionParser(String input) {
         this.input = input;
      }

      private long parse() {
         long value = this.parseExpression();
         if (this.index != this.input.length()) {
            throw new IllegalArgumentException("unexpected token");
         } else {
            return value;
         }
      }

      private long parseExpression() {
         long value = this.parseTerm();

         while (this.index < this.input.length() && (this.input.charAt(this.index) == '+' || this.input.charAt(this.index) == '-')) {
            char op = this.input.charAt(this.index++);
            long right = this.parseTerm();
            value = op == '+' ? Math.addExact(value, right) : Math.subtractExact(value, right);
         }

         return value;
      }

      private long parseTerm() {
         long value;
         for (value = this.parseFactor();
            this.index < this.input.length() && this.input.charAt(this.index) == '*';
            value = Math.multiplyExact(value, this.parseFactor())
         ) {
            this.index++;
         }

         return value;
      }

      private long parseFactor() {
         if (this.index >= this.input.length()) {
            throw new IllegalArgumentException("missing value");
         } else if (this.input.charAt(this.index) == '(') {
            this.index++;
            long value = this.parseExpression();
            if (this.index < this.input.length() && this.input.charAt(this.index++) == ')') {
               return value;
            } else {
               throw new IllegalArgumentException("missing parenthesis");
            }
         } else {
            int start = this.index;

            while (this.index < this.input.length() && Character.isDigit(this.input.charAt(this.index))) {
               this.index++;
            }

            if (start == this.index) {
               throw new IllegalArgumentException("missing number");
            } else {
               return Long.parseLong(this.input.substring(start, this.index));
            }
         }
      }
   }
}
