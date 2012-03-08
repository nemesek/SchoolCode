-- 1) square function
square n = n * n

-- 2) f2c function
f2c n = (n-32)* (5/9)

-- 3) average function, 
--total xs = foldl (+) 0 xs
--average ls = (realToFrac (total ls))/(realToFrac(length ls))
average ls = (realToFrac (foldl (+) 0 ls))/(realToFrac(length ls))

-- 4) myGcd function
myGcd a b = if(a == b) then a
            else if(a>b)
                then myGcd (a-b) b
                else
                    myGcd a (b-a)

-- 5) mySequence function					
mySequence a b = [a..b]

-- Bonus  Largest recorded Prime running for 1 hour was 17,061,623
isPrime x = if x == 2 then True else null [y |y<-(2:[3,5..floor (sqrt (fromIntegral x))]), x `mod` y ==0]
