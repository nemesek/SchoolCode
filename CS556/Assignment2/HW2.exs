defmodule HW2 do
  # Chapter 5 exercise 2
  def fizzbuzz(0,0,_), do: "FizzBuzz"
  def fizzbuzz(0,_,_), do: "Fizz"
  def fizzbuzz(_,0,_), do: "Buzz"
  def fizzbuzz(_,_,z), do:  z

  # Chapter 5 exercise 4
  # mrs = prefix.("Mrs") -> mrs.("Smith") -> "Mrs Smith"
  def prefix(pre), do: (fn post -> pre <> " " <> post end)

  # Chapter 6 exercise 4
  def sum(0), do: 0
  def sum(n) when n > 0 do n + sum(n-1) end
  def sum(n) do raise "Invalid input #{n}" end

  # Chapter 6 exercise 6
  defmodule Chop do
    def guess(actual, range) do
      low..high = range
      i = div(low + high,2)
      IO.puts("Is it #{i}")
      _makeGuess(actual,range,i)
    end

    defp _makeGuess(actual,_, guess) when actual == guess do actual end
    defp _makeGuess(actual, _..h, guess) when actual > guess do guess(actual, guess+1..h) end
    defp _makeGuess(actual, l.._, guess), do: guess(actual, l..guess - 1)
  end

  # Chapter 7 exercise 1
  def mapsum(list, func), do: _map(list,func) |> _sum()

  defp _map([], _), do: []
  defp _map([head|tail], func), do: [func.(head)| _map(tail,func)]
  defp _sum(list), do: _reduce(list, 0, &(&1 + &2))
  defp _reduce([], value, _), do: value
  defp _reduce([head|tail], value, func), do: _reduce(tail, func.(head,value), func)

  # Chapter 7 exercise 2
  def max([head|tail]), do: _max(tail,head)

  defp _max([], largest), do: largest
  defp _max([head|tail], largest) when head > largest do _max(tail,head) end
  defp _max([_|tail], largest), do: _max(tail,largest)

  # Chapter 10 exercise 5
  # all? implementation I only intended to do this one so I guess this
  # is the one that should be graded
  def all?([], _), do: true
  def all?([head|tail], func) do
    if func.(head) do
      all?(tail, func)
    else
      false
    end
  end

  # each implementation, did it for fun
  def each([], _), do: :ok
  def each([head|tail], func) do
    func.(head)
    each(tail,func)
  end

  # split implementation, did it for fun
  # just reused your implementation of take and reverse
  def split(list, n) do
     {
       _take(list,n),
       _reverse(_take(_reverse(list,[]), length(list) - n),[])
     }
   end

  defp _take([], _)            do [] end
  defp _take(_, n) when n <= 0 do [] end
  defp _take([x|xs], n)        do [ x | _take(xs,n-1) ] end
  defp _reverse([],ys)      do ys end
  defp _reverse([x|xs], ys) do _reverse(xs, [x|ys]) end
end
