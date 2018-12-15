export let useLocalStorage = true;

// export function initIndexDB(db, checkStorage) {
//     let requestDB = self.indexedDB.open('LAB_DB', 4);
//     let productsStore = null;

//     requestDB.onsuccess = function (event) {
//         // get database from event
//         db = event.target.result;
//         checkStorage();
//     };

//     requestDB.onerror = function (event) {
//         console.log('[onerror]', requestDB.error);
//     };

//     requestDB.onupgradeneeded = function (event) {
//         var db = event.target.result;
//         db.createObjectStore('fans', { keyPath: 'id', autoIncrement: true });
//         db.createObjectStore('news', { keyPath: 'id', autoIncrement: true });
//     };
// }