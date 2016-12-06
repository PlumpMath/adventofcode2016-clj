(ns adventofcode.day3
  (:require [clojure.string :as str]))

(def input (->> (slurp "resources/day3_input")
                str/split-lines
                (map str/trim)
                (map #(str/split % #" +"))
                (map (fn [line] (map #(Integer/parseInt (str/trim %)) line)))))

(defn triangle? [[a b c]]
  (and (> (+ a b) c)
       (> (+ b c) a)
       (> (+ c a) b)))

(count (filter triangle? input))
