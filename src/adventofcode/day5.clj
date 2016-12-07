(ns adventofcode.day5
  (:require [digest :refer [md5]]
            [clojure.string :as str]))

(defn password [door]
  (->> (range)
       (map #(md5 (str door %)))
       (filter #(.startsWith % "00000"))
       (map #(nth % 5))
       (take 8)
       (apply str)))

(time (password "ffykfhsq"))
"Elapsed time: 31595.127018 msecs"

;; part 2

(defn password2 [door]
  (loop [n 0
         remaining 8
         pass [nil nil nil nil nil nil nil nil]]
    (let [md5-sum (md5 (str door n))]
      (if (= 0 remaining)
        ;; when no more remaining, return the result
        (str/join pass)

        ;; if spots still remain to be filled..
        (if (.startsWith md5-sum "00000")
          (let [pos (- (int (nth md5-sum 5)) (int \0))
                passchar (nth md5-sum 6)]
            (if (and (>= 7 pos) (nil? (get pass pos)))
              (do (print md5-sum pos passchar "\n")
                  (recur (inc n)
                         (dec remaining)
                         (assoc pass pos passchar)))
              (recur (inc n) remaining pass)))
          (recur (inc n) remaining pass))))))

(time (password2 "ffykfhsq"))
"Elapsed time: 136544.772772 msecs"
