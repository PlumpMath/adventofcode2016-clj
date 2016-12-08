(ns adventofcode.day8
  (:require [clojure.string :as str]))

(defn parse [line]
  (let [nums (map read-string (re-seq #"\d+" line))]
    (cond
      (.startsWith line "rect") [:rect nums]
      (.startsWith line "rotate row") [:rotate-row nums]
      (.startsWith line "rotate col") [:rotate-col nums])))

(def input (->> (slurp "resources/day8_input")
                str/split-lines
                (map parse)))

(def blank-screen (vec (repeat 6 (vec (repeat 50 :off)))))

(defn rect [screen [n-col n-row]]
  (reduce (fn [m coord]
            (assoc-in m coord :on))
          screen
          (for [x (range n-col)
                y (range n-row)]
            [y x])))

(defn rotate-row [screen [y n]]
  (let [row (cycle (nth screen y))
        width (count (first screen))
        shift (- width (rem n width))]
    (->> (drop shift row)
         (take width)
         vec
         (assoc screen y))))

(defn transpose [screen]
  (apply (partial mapv vector) screen))

(defn rotate-col [screen [col n]]
  (-> (transpose screen)
      (rotate-row [col n])
      transpose))

(defn draw [m [instruction coords]]
  (case instruction
    :rect (rect m coords)
    :rotate-col (rotate-col m coords)
    :rotate-row (rotate-row m coords)))

(defn swipe [screen instructions]
  (reduce draw screen instructions))

;; Part 2

(defn print-screen [screen]
  (let [pixels (flatten screen)]
    (println (count (filter #{:on} pixels)))
    (->> pixels
         (map #(case %
                 :on "#"
                 :off " "))
         (partition 50)
         (map str/join)
         (map println))))

(print-screen (swipe blank-screen input))
