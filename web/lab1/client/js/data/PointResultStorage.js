import PointResult from "./PointResult.js";

class PointResultStorage {
  static STORAGE_KEY = "lab1-prs";
  #callbacks = {
    clear: [],
    insert: [],
  };

  constructor() {}

  on(type, callback) {
    this.#callbacks[type].push(callback);
  }

  #notify(type, data) {
    this.#callbacks[type].forEach((cb) => cb(data));
  }

  #serialize(list) {
    return JSON.stringify(list);
  }

  #deserialize(data) {
    return JSON.parse(data).map((i) => new PointResult(i));
  }

  #store(data) {
    localStorage.setItem(PointResultStorage.STORAGE_KEY, this.#serialize(data));
  }

  clear() {
    this.#store([]);
    this.#notify("clear");
  }

  get() {
    let data = localStorage.getItem(PointResultStorage.STORAGE_KEY);
    if (!data) {
      this.clear();
      return [];
    }
    return this.#deserialize(data);
  }

  add(data) {
    this.#store([...this.get(), data]);
    this.#notify("insert", data);
  }
}

export default PointResultStorage;
