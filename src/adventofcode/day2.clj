(ns adventofcode.day2
  (:require [clojure.string :as str]))

(defn parse-line [line]
  (map (comp keyword str) line))

(def input (->> (slurp "resources/day2_input")
                str/split-lines
                (map parse-line)))

(def coord->code
  {[0 2] 1 [1 2] 2 [2 2] 3
   [0 1] 4 [1 1] 5 [2 1] 6
   [0 0] 7 [1 0] 8 [2 0] 9})

(defn to-delta [direction]
  (condp = direction
    :L [-1 0]
    :R [1 0]
    :U [0 1]
    :D [0 -1]))

(defn move [[x y] direction]
  (let [mvmt (to-delta direction)
        new-place [(+ x (first mvmt))
                   (+ y (second mvmt))]]
    (for [coord new-place]
      (cond
        (> coord 2) 2
        (< coord 0) 0
        :else coord))))

(->> input
     (map #(reduce move [1 1] %))
     (map coord->code))
;; =>
;; (6 1 5 2 9)

;; 2
(def diamond-coords->code
  {[0 2] 1
   [-1 1] 2 [0 1] 3 [1 1] 4
   [-2 0] 5 [-1 0] 6 [0 0] 7 [1 0] 8 [2 0] 9
   [-1 -1] \A [0 -1] \B [1 -1] \C
   [0 -2] \D})

(defn move2 [[x y] direction]
  (let [mvmt (to-delta direction)
        new-place [(+ x (first mvmt))
                   (+ y (second mvmt))]]
    (if (> (apply + (map #(Math/abs %) new-place)) 2)
      [x y]
      new-place)))

(->> input
     (map #(reduce move2 [-2 0] %))
     (map diamond-coords->code))
;; =>
;; (\C 2 \C 2 8)
