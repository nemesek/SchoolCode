defmodule Star do
  import :timer
  def sad_function do
    sleep 500
    exit(:boom)
  end

  def run(n,m) do _run(n) end
  defp _run(0) do IO.puts "DONE" end
  defp _run(n) do
    Process.flag(:trap_exit,true)
    spawn_link(Star,:sad_function,[])
    receive do
      msg -> IO.puts "MESSAGE RECEIVED: #{inspect msg}"
      _run(n-1)
      end
  end
end

Star.run(4,3)
