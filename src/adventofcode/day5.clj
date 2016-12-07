(ns adventofcode.day5
  (:require [digest :refer [md5]]
            [clojure.string :as str]))

(md5 "abc3231929")
;; => "00000155f8105dff7f56ee10fa9b9abd"

(def door-id "ffykfhsq")

(->> (range)
     (map #(md5 (str "ffykfhsq" %)))
     (filter #(.startsWith % "00000"))
     (map #(nth % 5))
     (take 8)
     (apply str))

;; part 2

(loop [n 0
       remaining 8
       pass [nil nil nil nil nil nil nil nil]]
  (let [md5-sum (md5 (str "ffykfhsq" n))]
    (if (= 0 remaining)
      ;; when no more remaining, return the result
      (str/join pass)

      ;; if spots still remain to be filled..
      (if (.startsWith md5-sum "00000")
        (let [pos (- (int (nth md5-sum 5)) (int \0))
              passchar (nth md5-sum 6)]
          (if (and (>= 7 pos) (nil? (get pass pos)))
            (do (print md5-sum pos passchar "\n")
                (recur (inc n) (dec remaining) (assoc pass pos passchar)))
            (recur (inc n) remaining pass)))
        (recur (inc n) remaining pass)))))
