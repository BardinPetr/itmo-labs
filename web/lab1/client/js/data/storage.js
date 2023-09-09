import PointResult from "./PointResult.js";

class PointResultStorage {
  static STORAGE_KEY = "lab1-prs";
  #callbacks = [];

  constructor() {}

  onChange(callback) {
    this.#callbacks.push(callback);
  }

  #notify(data) {
    this.#callbacks.forEach((cb) => cb(data));
  }

  #serialize(list) {
    return JSON.stringify(list);
  }

  #deserialize(data) {
    return JSON.parse(data).map((i) => new PointResult(i));
  }

  #store(data) {
    this.#notify(data);
    localStorage.setItem(PointResultStorage.STORAGE_KEY, this.#serialize(data));
  }

  clear() {
    this.#store([]);
  }

  get() {
    let data = localStorage.getItem(PointResultStorage.STORAGE_KEY);
    if (!data) {
      this.clear();
      return [];
    }
    return this.#deserialize(data);
  }

  add(result) {
    this.#store([...this.get(), result]);
  }
}

export default PointResultStorage;
