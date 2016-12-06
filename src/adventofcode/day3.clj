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


;; Part 2

(def vertically-grouped
  (reduce (fn [acc triplet]
            (concat acc (for [i (range 3)]
                          (map #(nth % i) triplet))))
          '()
          (partition 3 input)))

(count (filter triangle? vertically-grouped))
