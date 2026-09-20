const root = document.documentElement;
root.classList.add('mist-ui');
const motion = matchMedia('(prefers-reduced-motion: reduce)');
const seenKey = 'mist-introduction-v4';
const heroUrl = new URL('./media/mist-character.png?v=cat-20260921', import.meta.url).href;
let pending = false, intro = null, opened = false, priorFocus = null, priorInert = false;
let dispose = () => {};
const arrow = '<svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" aria-hidden="true"><path d="M4 12h15M13 5l7 7-7 7"/></svg>';
const reel = '<svg viewBox="0 0 370 94" fill="none" stroke="currentColor" stroke-width="1" aria-hidden="true"><circle cx="47" cy="47" r="32"/><circle cx="47" cy="47" r="10"/><path d="M47 5v13m0 58v13M5 47h13m58 0h13M99 47h33l24-24h38l28 47h40l25-35h70" stroke-dasharray="5 5"/><path d="M99 12h255M99 83h255" opacity=".3"/><circle cx="156" cy="23" r="4"/><circle cx="222" cy="70" r="4"/><circle cx="287" cy="35" r="4"/></svg>';
const lineSet = (count, className) => `<div class="${className}">${Array.from({ length: count }, (_, index) => `<i style="--line:${index}"></i>`).join('')}</div>`;
const entryScene = `
  <div class="mist-entry" aria-hidden="true">
    <div class="mist-entry-mark"><span>mist<b>.</b></span><i></i></div>
    <div class="mist-entry-grid">${lineSet(49, 'mist-entry-grid-x')}${lineSet(28, 'mist-entry-grid-y')}</div>
    <div class="mist-entry-blueprint">
      <div class="mist-entry-header-frame">
        <i class="mist-entry-brand-frame"></i><i class="mist-entry-nav-frame"></i><i class="mist-entry-tool-frame"></i>
      </div>
      <div class="mist-entry-hero-frame">
        <div class="mist-entry-copy-frame"><i class="mist-entry-meta-frame"></i><i class="mist-entry-title-frame"></i><i class="mist-entry-title-frame mist-entry-title-frame--short"></i><i class="mist-entry-summary-frame"></i><i class="mist-entry-summary-frame mist-entry-summary-frame--short"></i><i class="mist-entry-action-frame"></i></div>
        <div class="mist-entry-portrait-frame"><i></i><i></i></div>
        <div class="mist-entry-signal-frame"><i></i><i></i><i></i><i></i><i></i><i></i></div>
      </div>
    </div>
  </div>`;
let carouselLibrary;
function loadCarousel() {
  return carouselLibrary ||= new Promise(resolve => {
    const script = document.createElement('script');
    script.src = new URL('./vendor/embla-carousel.umd.js', import.meta.url).href;
    script.onload = () => resolve(window.EmblaCarousel);
    script.onerror = () => resolve(null);
    document.head.append(script);
  });
}
function hasSeen() { try { return sessionStorage.getItem(seenKey) === '1'; } catch { return opened; } }
function closeIntro() {
  if (!intro) return;
  dispose(); intro.remove(); intro = null;
  document.getElementById('app').inert = priorInert;
  root.classList.remove('mist-intro-open');
  try { sessionStorage.setItem(seenKey, '1'); } catch {}
  if (priorFocus?.isConnected && priorFocus.matches('button,a,input,[tabindex]')) priorFocus.focus({ preventScroll: true });
  else document.querySelector('.auth-card input')?.focus({ preventScroll: true });
}
function openIntro() {
  if (intro) return;
  opened = true; priorFocus = document.activeElement;
  const app = document.getElementById('app'); priorInert = app.inert;
  intro = document.createElement('div'); intro.className = 'mist-intro';
  intro.setAttribute('role', 'dialog'); intro.setAttribute('aria-modal', 'true'); intro.setAttribute('aria-label', 'Mist 欢迎页'); intro.tabIndex = -1;
  intro.innerHTML = `
    ${entryScene}
    <header class="mist-site-header"><button class="mist-wordmark" data-jump="0" aria-label="返回介绍首屏">mist<span>.</span></button>
      <nav aria-label="欢迎页导航"><button data-jump="0" aria-current="true">首页</button><button data-jump="1">探索</button><button data-jump="2">开始使用</button></nav>
      <button class="mist-header-login" data-enter>登录 ${arrow}</button></header>
    <nav class="mist-side-nav" aria-label="章节"><button data-jump="0" aria-current="true">01 / 首页</button><button data-jump="1">02 / 发现</button><button data-jump="2">03 / 开始</button></nav>
    <main class="mist-pages">
      <section class="mist-section mist-hero" aria-labelledby="mist-hero-heading">
        <div class="mist-hero-copy"><span class="mist-eyebrow">MIST</span>
          <h1 id="mist-hero-heading">嗨，欢迎来到 Mist。<br>把时间，留给喜欢的故事。</h1>
          <p>电影、剧集，还有下一部想看的。<br>在这里，慢慢发现。</p>
          <div class="mist-actions"><button class="mist-button" data-enter>进入 Mist <span>${arrow}</span></button><button class="mist-button mist-button--outline" data-jump="1">向下探索 <span>↓</span></button></div>
        </div>
        <div class="mist-hero-figure"><img src="${heroUrl}" alt="Mist 动物伙伴插画" width="1024" height="1536" fetchpriority="high" draggable="false"></div>
        <div class="mist-technical" aria-hidden="true">${reel}</div>
        <div class="mist-hero-foot"><span>私人影库</span><button data-jump="1" aria-label="向下查看探索章节">继续浏览 ↓</button></div>
      </section>
      <section class="mist-section mist-explore" aria-labelledby="mist-explore-heading">
        <div class="mist-section-copy"><span class="mist-eyebrow">发现</span><h2 id="mist-explore-heading">总有一个故事，<br>值得停留。</h2><p>进入媒体库，发现电影与剧集。<br>想找的内容、想看的下一部，都从这里开始。</p><div class="mist-feature-list"><span>媒体浏览</span><span>内容搜索</span><span>求片中心</span><span>账户管理</span></div><div class="mist-gallery-controls"><button class="mist-icon-button" data-prev aria-label="上一张">←</button><button class="mist-icon-button" data-next aria-label="下一张">→</button><span>拖动卡片浏览</span></div></div>
        <div class="mist-gallery" role="region" aria-roledescription="轮播" aria-label="光影展示" tabindex="0"><div class="mist-gallery-track">
          <article class="mist-photo" aria-label="1 / 3 雾中有光"><div class="mist-photo-inner"><img src="/mistbot.png" alt="蓝色月光与薄雾" loading="lazy" draggable="false"><div><span>01</span><h3>雾中有光</h3><span>推荐</span></div></div></article>
          <article class="mist-photo" aria-label="2 / 3 城市漫游"><div class="mist-photo-inner"><img src="/mist-assets/cyber-idol-city-CMKiGS8T.png" alt="夜色中的未来城市" loading="lazy" draggable="false"><div><span>02</span><h3>城市漫游</h3><span>探索</span></div></div></article>
          <article class="mist-photo" aria-label="3 / 3 故事之中"><div class="mist-photo-inner"><img class="mist-photo-character" src="${heroUrl}" alt="Mist 动物伙伴" loading="lazy" draggable="false"><div><span>03</span><h3>故事之中</h3><span>片单</span></div></div></article>
        </div></div>
      </section>
      <section class="mist-section mist-start" aria-labelledby="mist-start-heading"><span class="mist-eyebrow">开始</span><h2 id="mist-start-heading">准备好，<br>看点喜欢的。</h2><button class="mist-button" data-enter>开始使用 <span>${arrow}</span></button><div class="mist-start-rule"></div><p>已有账户直接登录 · 新用户请使用有效邀请</p><span class="mist-start-word" aria-hidden="true">mist.</span></section>
    </main>`;
  document.body.append(intro); app.inert = true; root.classList.add('mist-intro-open');
  const container = intro;
  const entry = container.querySelector('.mist-entry');
  let entryTimer = 0;
  const finishEntry = () => {
    if (!entry || entry.classList.contains('is-finished')) return;
    entry.classList.add('is-finished');
    container.classList.add('mist-entry-complete');
    window.clearTimeout(entryTimer);
    window.setTimeout(() => entry.remove(), 260);
  };
  if (motion.matches) finishEntry();
  else {
    entry.addEventListener('animationend', event => {
      if (event.target === entry && event.animationName === 'mist-entry-surface') finishEntry();
    });
    entryTimer = window.setTimeout(finishEntry, 4000);
  }
  const sections = [...container.querySelectorAll('.mist-section')];
  const jump = i => container.scrollTo({ top: sections[i].offsetTop - 65, behavior: motion.matches ? 'instant' : 'smooth' });
  container.querySelectorAll('[data-jump]').forEach(button => button.addEventListener('click', () => jump(Number(button.dataset.jump))));
  container.querySelectorAll('[data-enter]').forEach(button => button.addEventListener('click', closeIntro));
  const intersection = new IntersectionObserver(entries => {
    for (const entry of entries) if (entry.isIntersecting) {
      const selected = sections.indexOf(entry.target);
      container.querySelectorAll('[data-jump]').forEach(button => {
        if (Number(button.dataset.jump) === selected) button.setAttribute('aria-current', 'true'); else button.removeAttribute('aria-current');
      });
    }
  }, { root: container, rootMargin: '-20% 0px -45% 0px', threshold: 0 });
  sections.forEach(section => intersection.observe(section));
  let carousel;
  const gallery = container.querySelector('.mist-gallery'), prev = container.querySelector('[data-prev]'), next = container.querySelector('[data-next]');
  const updateButtons = () => { prev.disabled = !carousel.canScrollPrev(); next.disabled = !carousel.canScrollNext(); };
  loadCarousel().then(Embla => {
    if (!container.isConnected || !Embla) return;
    carousel = Embla(gallery, { align: 'start', containScroll: 'trimSnaps', duration: motion.matches ? 0 : 28 });
    gallery.classList.add('mist-gallery--enhanced'); carousel.on('select', updateButtons).on('reInit', updateButtons); updateButtons();
  });
  const move = direction => {
    if (carousel) direction < 0 ? carousel.scrollPrev(motion.matches) : carousel.scrollNext(motion.matches);
    else gallery.scrollBy({ left: direction * 300, behavior: motion.matches ? 'instant' : 'smooth' });
  };
  prev.addEventListener('click', () => move(-1)); next.addEventListener('click', () => move(1));
  gallery.addEventListener('keydown', event => { if (['ArrowLeft','ArrowRight'].includes(event.key)) { event.preventDefault(); move(event.key === 'ArrowLeft' ? -1 : 1); } });
  container.addEventListener('keydown', event => {
    if (event.key === 'Escape') closeIntro();
    if (event.key !== 'Tab') return;
    const items = [...container.querySelectorAll('button:not(:disabled),[tabindex="0"]')].filter(el => el.getClientRects().length);
    if (event.shiftKey && [items[0],container].includes(document.activeElement)) { event.preventDefault(); items.at(-1).focus(); }
    else if (!event.shiftKey && document.activeElement === items.at(-1)) { event.preventDefault(); items[0].focus(); }
  });
  dispose = () => { window.clearTimeout(entryTimer); intersection.disconnect(); carousel?.destroy(); };
  container.focus({ preventScroll: true });
}
function enhance() {
  pending = false;
  const auth = document.querySelector('.auth-page'); root.classList.toggle('mist-auth-active', Boolean(auth));
  if (!auth && intro) closeIntro();
  if (!auth) return;
  const invite = auth.classList.contains('auth-page--invite'), brand = auth.querySelector('.auth-card > .auth-brand');
  if (brand && !brand.querySelector('.mist-form-intro')) {
    const heading = document.createElement('div'); heading.className = 'mist-form-intro';
    heading.innerHTML = `<span class="mist-eyebrow">MIST</span><h1>${invite ? '创建你的账户。' : '好久不见。'}</h1><p>${invite ? '填写邀请信息，开始使用 Mist。' : '登录 Mist，继续你的光影旅程。'}</p>`;
    brand.append(heading);
  }
  if (!auth.querySelector('.mist-auth-art')) {
    const art = document.createElement('div'); art.className = 'mist-auth-art'; art.setAttribute('aria-hidden', 'true');
    art.innerHTML = `<img src="${heroUrl}" alt="" width="1024" height="1536"><span>私人影库</span>`;
    (auth.querySelector('.auth-page-content') || auth).append(art);
  }
  if (!auth.querySelector('.mist-auth-header')) {
    const header = document.createElement('div'); header.className = 'mist-auth-header';
    header.innerHTML = '<span class="mist-wordmark">mist<span>.</span></span><button class="mist-replay" type="button">查看介绍 ↗</button>';
    header.querySelector('button').addEventListener('click', openIntro); auth.prepend(header);
  }
  if (!opened && !hasSeen()) openIntro();
}
new MutationObserver(() => { if (!pending) { pending = true; requestAnimationFrame(enhance); } }).observe(document.getElementById('app'), { childList: true, subtree: true });
enhance();
