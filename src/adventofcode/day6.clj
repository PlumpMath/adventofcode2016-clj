(ns adventofcode.day6
  (:require [clojure.string :as str]))

(def input (str/split-lines (slurp "resources/day6_input")))

(def columns (for [i (range 8)]
               (str/join (map #(get % i) input))))

(->> columns
     (map #(key (apply max-key val (frequencies %))))
     str/join)

(->> columns
     (map #(key (apply min-key val (frequencies %))))
     str/join)
