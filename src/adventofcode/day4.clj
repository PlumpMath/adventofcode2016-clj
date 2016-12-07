(ns adventofcode.day4
  (:require [clojure.core.match :refer [match]]
            [clojure.string :as str]))

(defn parse-line [s]
  (let [[front chsum] (re-seq #"[\w-]+" s)
        name (str/replace front #"[\d-]" "")
        id (Integer/parseInt (str/join (take-last 3 front)))]
    {:name name :id id :chsum chsum}))

(def input (->> (slurp "resources/day4_input")
                str/split-lines
                (map parse-line)))

(defn hash [name]
  (->> (frequencies name)
       (sort-by key)
       (sort-by #(get % 1) >)
       (map first)
       (take 5)
       str/join))

(defn real? [{:keys [name chsum]}]
  (= (hash name) chsum))

(defn check [{:keys [id] :as room}]
  (if (real? room) id 0))

(apply + (map check input))

;; Part 2

(def real-rooms (filter real? input))

(def alphabet "abcdefghijklmnopqrstuvwxyz")

(defn decypher [{:keys [name id] :as room}]
  (let [idxs (for [l name] (get (zipmap alphabet (range)) l))
        rotated (map (partial + id) idxs)]
    (assoc room :cypher
           (str/join (for [i rotated]
                       (nth (cycle alphabet) i))))))

(->> real-rooms
     (map decypher)
     (map #(when (re-find #"northpole" (:cypher %))
             ((juxt :cypher :id) %)))
     (remove nil?))
