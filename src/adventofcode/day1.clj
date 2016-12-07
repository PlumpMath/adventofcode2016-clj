(ns adventofcode.day1
  (:require [clojure.core.match :refer [match]]))

(def raw-input ["L1" "L3" "L5" "L3" "R1" "L4" "L5" "R1" "R3" "L5" "R1" "L3" "L2" "L3" "R2" "R2" "L3" "L3" "R1" "L2" "R1" "L3" "L2" "R4" "R2" "L5" "R4" "L5" "R4" "L2" "R3" "L2" "R4" "R1" "L5" "L4" "R1" "L2" "R3" "R1" "R2" "L4" "R1" "L2" "R3" "L2" "L3" "R5" "L192" "R4" "L5" "R4" "L1" "R4" "L4" "R2" "L5" "R45" "L2" "L5" "R4" "R5" "L3" "R5" "R77" "R2" "R5" "L5" "R1" "R4" "L4" "L4" "R2" "L4" "L1" "R191" "R1" "L1" "L2" "L2" "L4" "L3" "R1" "L3" "R1" "R5" "R3" "L1" "L4" "L2" "L3" "L1" "L1" "R5" "L4" "R1" "L3" "R1" "L2" "R1" "R4" "R5" "L4" "L2" "R4" "R5" "L1" "L2" "R3" "L4" "R2" "R2" "R3" "L2" "L3" "L5" "R3" "R1" "L4" "L3" "R4" "R2" "R2" "R2" "R1" "L4" "R4" "R1" "R2" "R1" "L2" "L2" "R4" "L1" "L2" "R3" "L3" "L5" "L4" "R4" "L3" "L1" "L5" "L3" "L5" "R5" "L5" "L4" "L2" "R1" "L2" "L4" "L2" "L4" "L1" "R4" "R4" "R5" "R1" "L4" "R2" "L4" "L2" "L4" "R2" "L4" "L1" "L2" "R1" "R4" "R3" "R2" "R2" "R5" "L1" "L2"])

(defn parse-input [s]
  [(keyword (str (first s))) (Integer. (apply str (rest s)))])

(def input-vec (map parse-input raw-input))

(def input-stream (flatten (map (fn [[side count]]
                                  (cons side (repeat (dec count) :F))) input-vec)))

(defn get-cardinal [facing direction]
  (match [facing direction]
    [:north :R] :east
    [:north :L] :west
    [:east :R] :south
    [:east :L] :north
    [:south :R] :west
    [:south :L] :east
    [:west :R] :north
    [:west :L] :south
    [facing :F] facing))

(defn step [{:keys [loc facing]} direction count]
  (let [movement (match [direction]
                   [:north] [0 count]
                   [:south] [0 (- count)]
                   [:east] [count 0]
                   [:west] [(- count) 0])]
    {:loc [(+ (first loc) (first movement))
           (+ (second loc) (second movement))]
     :facing direction}))

(defn single-step [from toward]
  (step from toward 1))

(defn move [{:keys [loc facing] :as current} [side count]]
  (let [direction (get-cardinal facing side)]
    (step current direction count)))

(defn move-in-single-steps [{:keys [loc facing] :as current} side]
  (let [direction (get-cardinal facing side)]
    (single-step current direction)))

(->> (reduce move
             {:loc [0 0] :facing :north}
             input-vec)
     :loc
     (map #(Math/abs %))
     (apply +))

;; Part 2
(def path (map :loc (reductions move-in-single-steps
                                {:loc [0 0] :facing :north}
                                input-stream)))

(let [repeated-location (loop [acc #{}
                               step (first path)
                               rem (rest path)]
                          (if (acc step)
                            step
                            (recur (conj acc step)
                                   (first rem)
                                   (next rem))))]
  (->> repeated-location
       (map #(Math/abs %))
       (apply +)))
