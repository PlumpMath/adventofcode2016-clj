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
