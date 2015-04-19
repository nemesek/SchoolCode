defmodule MS do
  def start(n) do _run(n,[])
  end

  defp _run(0,_) do IO.puts "Started them all" end
  defp _run(n, pids) do
    Process.flag(:trap_exit,true)
    pid = spawn_link(MS,:slave,[])
    _run(n-1,[pid|pids])
  end

  def slave do
    IO.puts "Started"
  end
end
