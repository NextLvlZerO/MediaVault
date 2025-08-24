
export function getCookie(name) {
  const value = `; ${document.cookie}`;
  const parts = value.split(`; ${name}=`);
  if (parts.length == 2) {
    return parts.pop().split(';').shift();
  }
  return null;
}


export function deleteAllCookies() {
  const cookies = document.cookie.split(";");

  for (let cookie of cookies) {
    const eqPos = cookie.indexOf("=");
    const name = eqPos > -1 ? cookie.substring(0, eqPos) : cookie;

    document.cookie = name.trim() +
      "=;expires=Thu, 01 Jan 1970 00:00:00 GMT;path=/";
  }
}
