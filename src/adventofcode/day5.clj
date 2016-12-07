(ns adventofcode.day5
  (:require [digest :refer [md5]]
            [clojure.string :as str]))

(md5 "abc3231929")
;; => "00000155f8105dff7f56ee10fa9b9abd"

(def door-id "ffykfhsq")

(loop [n 0
       remaining 8
       acc ""]
  (let [md5-sum (md5 (str "ffykfhsq" n))]
    (if (= 0 remaining)
      acc
      (if (.startsWith md5-sum "00000")
        (do (print md5-sum (nth md5-sum 5) "\n")
            (recur (inc n) (dec remaining) (str acc (nth md5-sum 5))))
        (recur (inc n) remaining acc)))))

;; part 2

(loop [n 0
       remaining 8
       acc [nil nil nil nil nil nil nil nil]]
  (let [md5-sum (md5 (str "ffykfhsq" n))]
    (if (= 0 remaining)
      (str/join acc)
      (if (.startsWith md5-sum "00000")
        (let [pos (- (int (nth md5-sum 5)) (int \0))
              passchar (nth md5-sum 6)]
          (if (and (>= 7 pos) (nil? (get acc pos)))
            (do (print md5-sum pos passchar "\n")
                (recur (inc n) (dec remaining) (assoc acc pos passchar)))
            (recur (inc n) remaining acc)))
        (recur (inc n) remaining acc)))))
