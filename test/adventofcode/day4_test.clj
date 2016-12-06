(ns adventofcode.day4-test
  (:require [adventofcode.day4 :refer :all]
            [clojure.test :refer :all]))

(deftest check-test
  (is (= (check (parse-line "not-a-real-room-404[oarel]"))
         404))
  (is (= (check (parse-line "totally-real-room-200[decoy]"))
         0))
  (is (= (check (parse-line "a-b-c-d-e-f-g-h-987 [abcde]"))
         987))
  (is (= (check (parse-line "aaaaa-bbb-z-y-x-123[abxyz]"))
         123))
  (is (= (check (parse-line "aabbzzccc123[cbaz]"))
         0)))
