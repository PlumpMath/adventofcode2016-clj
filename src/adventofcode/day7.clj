(ns adventofcode.day7
  (:require [clojure.string :as str]))

(defn parse-ip
  [ip]
  (let [xs (str/split ip #"[\[\]]")
        supernet (map #(if (even? (.indexOf xs %))))
        #_(for [i (range 0 (count xs) 2)] (get xs i))
        hypernet (for [i (range 1 (count xs) 2)] (get xs i))]
    {:supernet supernet :hypernet hypernet}))

(def input (map parse-ip (str/split-lines (slurp "resources/day7_input"))))

(defn abba? [s]
  (some (fn [[a b c d]]
          (and (= a d)
               (= b c)
               (not= a b)))
        (partition 4 1 s)))

(defn tls? [{:keys [supernet hypernet]}]
  (boolean
   (and (some abba? supernet)
        (not-any? abba? hypernet))))

(count (filter true? (map tls? input)))
;; => 110

;; Part 2

(defn find-aba [s]
  (->> (partition 3 1 s)
       (map (fn [[a b c]]
              (when (and (= a c)
                         (not= a b))
                [a b])))
       (remove nil?)))

(defn bab? [[a b] s]
  (->> (partition 3 1 s)
       (map (fn [[x y z]]
              (when (and (= x z b) (= y a))
                (str/join [x y z]))))))

(mapcat find-aba ["zaz" "abaccc"])
;; =>
;; ([\z \a] [\a \b])

(bab? [\a \b] "babzzz")
;; =>
;; ("bab" nil nil nil)

(defn ssl? [{:keys [supernet hypernet]}]
  (let [ab-pairs (mapcat find-aba supernet)]
    (->> (for [pair ab-pairs]
           (mapcat #(bab? pair %) hypernet))
         flatten
         (remove nil?))))

(count (filter not-empty (map ssl? input)))
;; => 242
