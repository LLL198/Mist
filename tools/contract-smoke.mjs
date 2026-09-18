const oldBase = process.env.OLD_BASE ?? 'http://127.0.0.1:8081';
const newBase = process.env.NEW_BASE ?? 'http://127.0.0.1:8082';

const cases = [
  { name: 'user existence', method: 'POST', path: '/api/embyUser/userExist', body: {} },
  { name: 'unauthenticated profile', method: 'GET', path: '/api/rose/profile' },
  { name: 'unauthenticated scheduled task', method: 'GET', path: '/api/scheduledTask/listAll' },
  { name: 'invalid system config request', method: 'POST', path: '/api/systemConfig/isEnabled', body: {} },
  {
    name: 'invalid login remains rejected',
    method: 'POST',
    path: '/api/embyUser/login',
    body: { userName: '__contract_invalid_user__', password: 'invalid-password', embyInfoId: null },
  },
];

async function request(base, test) {
  const response = await fetch(`${base}${test.path}`, {
    method: test.method,
    headers: test.body ? { 'content-type': 'application/json' } : undefined,
    body: test.body ? JSON.stringify(test.body) : undefined,
  });
  const text = await response.text();
  let body = text;
  try { body = JSON.parse(text); } catch { /* preserve non-JSON body */ }
  return { status: response.status, body };
}

function stable(value) {
  if (Array.isArray(value)) return value.map(stable);
  if (value && typeof value === 'object') {
    return Object.fromEntries(Object.keys(value).sort().map((key) => [key, stable(value[key])]));
  }
  return value;
}

let failures = 0;
for (const test of cases) {
  const oldResult = await request(oldBase, test);
  const newResult = await request(newBase, test);
  const same = oldResult.status === newResult.status
    && JSON.stringify(stable(oldResult.body)) === JSON.stringify(stable(newResult.body));
  console.log(`${same ? 'PASS' : 'FAIL'} ${test.name}: old=${oldResult.status}, new=${newResult.status}`);
  if (!same) {
    console.log(`  old: ${JSON.stringify(oldResult.body)}`);
    console.log(`  new: ${JSON.stringify(newResult.body)}`);
    failures += 1;
  }
}

if (failures) process.exitCode = 1;
