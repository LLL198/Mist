import fs from "node:fs";
import path from "node:path";
import { fileURLToPath } from "node:url";

const root = path.resolve(path.dirname(fileURLToPath(import.meta.url)), "..");
const bundleDirectories = [
  path.join(root, "frontend", "dist", "assets"),
  path.join(root, "frontend", "dist", "mist-assets"),
];

const replaceIfPresent = (source, from, to) => {
  return source.replaceAll(from, to);
};

for (const directory of bundleDirectories) {
  if (!fs.existsSync(directory)) continue;

  for (const fileName of ["index-MistBlue.js", "index-CUVHiK7q.js"]) {
    const filePath = path.join(directory, fileName);
    if (!fs.existsSync(filePath)) continue;

    let source = fs.readFileSync(filePath, "utf8");
    source = replaceIfPresent(
      source,
      ',"assets/ActivationView-CsMmAptN.js"',
      "",
      `${fileName}: activation preload`,
    );
    source = replaceIfPresent(
      source,
      ',"assets/LicenseView-ASEXEnev.js"',
      "",
      `${fileName}: license preload`,
    );
    source = replaceIfPresent(
      source,
      'throw[422,453].includes(f)&&typeof window<"u"&&(Cx(),window.location.pathname!=="/activation"&&window.location.replace("/activation")),new Er',
      "throw new Er",
      `${fileName}: license failure redirect`,
    );
    const localLicenseHelpers =
      'function qD(){return Promise.resolve({valid:!0})}function YD(){return Promise.resolve({valid:!0})}function KD(){return Promise.resolve({valid:!0})}function XD(){return Promise.resolve({valid:!0})}';
    source = replaceIfPresent(
      source,
      'function qD(){return R("/license/status",{method:"GET"})}function YD(){return R("/license/detail",{method:"GET"})}function KD(e){return R("/license/activate",{method:"POST",body:{licenseCode:e}})}function XD(){return R("/license/unbind",{method:"POST"})}',
      // Keep the extracted module's internal export contract stable for
      // legacy chunks, but make every former license helper a local no-op.
      // No network endpoint or activation state remains in the application.
      localLicenseHelpers,
      `${fileName}: license API helpers`,
    );
    // Some already-cleaned extractions no longer contain the original helper
    // sequence. Reinsert only the local compatibility exports immediately
    // before the next API helper so all legacy chunks still load.
    if (!source.includes(localLicenseHelpers)) {
      source = replaceIfPresent(
        source,
        '}function ZD(){return R("/embyUser/logout",{method:"POST",body:new URLSearchParams})}',
        `}${localLicenseHelpers}function ZD(){return R("/embyUser/logout",{method:"POST",body:new URLSearchParams})}`,
        `${fileName}: local license export compatibility`,
      );
    }
    source = replaceIfPresent(
      source,
      '{path:"/activation",name:"activation",component:()=>Se(()=>import("./ActivationView-CsMmAptN.js"),__vite__mapDeps([7,2])),meta:{public:!0}},',
      "",
      `${fileName}: activation route`,
    );
    source = replaceIfPresent(
      source,
      '{path:"/license",name:"license",component:()=>Se(()=>import("./LicenseView-ASEXEnev.js"),__vite__mapDeps([72,9,2,1,14])),meta:{admin:!0,adminMenu:"license"}},',
      "",
      `${fileName}: license route`,
    );
    source = source.replaceAll('"license_admin_license_code",', "");
    if (/\/activation|\/license\/|ActivationView|LicenseView|licenseCode|license_admin_license_code/.test(source)) {
      throw new Error(`Frontend bundle still contains license feature markers: ${filePath}`);
    }
    fs.writeFileSync(filePath, source, "utf8");
  }

  const appShellPath = path.join(directory, "AppShell.vue_vue_type_script_setup_true_lang-D22dcDAa.js");
  if (fs.existsSync(appShellPath)) {
    let source = fs.readFileSync(appShellPath, "utf8");
    source = replaceIfPresent(
      source,
      ',{title:"授权信息",icon:"mdi-shield-star-outline",to:"/license",permissionKey:"license"}',
      "",
      "AppShell: license menu item",
    );
    if (/\/license|授权信息/.test(source)) {
      throw new Error(`App shell still contains license menu markers: ${appShellPath}`);
    }
    fs.writeFileSync(appShellPath, source, "utf8");
  }

  for (const fileName of fs.readdirSync(directory).filter((name) => name.endsWith(".js"))) {
    const filePath = path.join(directory, fileName);
    let source = fs.readFileSync(filePath, "utf8");
    // Vite's lazy chunks still carry the original preload map. Point that
    // runtime dependency at Mist's single entry bundle; preloading the old
    // duplicate entry bundle mounts a second Vue app into #app.
    source = source.replaceAll('"assets/index-CUVHiK7q.js"', '"assets/index-MistBlue.js"');
    source = source.replaceAll('from"./index-CUVHiK7q.js"', 'from"./index-MistBlue.js"');
    if (fileName === "LoginView-BmEVo3yO.js") {
      // Remove the stale activation controls from the extracted login render
      // function. The surrounding login, registration and Telegram flows are
      // retained unchanged.
      const alertStart = source.indexOf('Se.value?(U(),O(Q,{key:0,type:"warning"');
      const formStart = alertStart < 0 ? -1 : source.indexOf(',b("form",{class:"auth-form"', alertStart);
      if (alertStart >= 0 && formStart > alertStart) {
        source = source.slice(0, alertStart) + source.slice(formStart + 1);
      }
      for (const activationStart of [
        ',mn.value?(U(),O(R,{key:0,size:"large",variant:"tonal","prepend-icon":"mdi-key-chain"',
        ',la.value?(U(),O(R,{key:2,icon:"mdi-credit-card-plus-outline"',
      ]) {
        const start = source.indexOf(activationStart);
        if (start < 0) continue;
        const endMarker = ':j("",!0)';
        const end = source.indexOf(endMarker, start);
        if (end >= 0) source = source.slice(0, start) + source.slice(end + endMarker.length);
      }
      source = source.replaceAll('At=W(()=>Ae.value?Ae.value.valid!==!0:!1)', 'At=W(()=>!1)');
      source = source.replaceAll('&&!At.value', '');
      source = source.replaceAll(',ua(),ga(),ya()}', ',ua(),ga()}');
      source = source.replaceAll(
        'if(At.value){i.value=Se.value||"系统授权未激活，请先激活授权码。",a.warning(i.value);return}',
        "",
      );
      source = source.replaceAll(
        'c instanceof Sn?[422,450,453].includes(c.code)?"系统授权未完成或已失效，请先激活授权码。":c.code===471?',
        'c instanceof Sn?c.code===471?',
      );
      source = source.replaceAll(
        ',t instanceof Sn&&[422,450,453].includes(t.code)&&(Se.value=i.value,Ae.value={valid:!1})',
        "",
      );
    }
    source = source.replaceAll('"license_admin_license_code",', "");
    if (source.includes("license_admin_license_code")) {
      throw new Error(`Frontend bundle still contains the legacy license config key: ${filePath}`);
    }
    fs.writeFileSync(filePath, source, "utf8");
  }

  for (const fileName of ["ActivationView-CsMmAptN.js", "LicenseView-ASEXEnev.js"]) {
    const filePath = path.join(directory, fileName);
    if (fs.existsSync(filePath)) fs.unlinkSync(filePath);
  }
}

console.log("Removed product-license UI and API code from frontend/dist bundles.");
