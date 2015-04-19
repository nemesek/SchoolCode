defmodule Star do
  def process() do
    receive do
      {:ok,msg} -> IO.puts "MSG: #{msg}"
      {:shutdown} -> exit(:boom)
    end
  end

  def run(n,m) do _run(n,m) end
  defp _run(0,_) do
     IO.puts "DONE SPAWNING"
  end
  defp _run(n,m) do
    Process.flag(:trap_exit,true)
    pid = spawn_link(Star,:process,[])
    #send pid, {:ok, "Blah Blah"}
    sendMessages(m,pid)
    _run(n-1,m)
  end

  def sendMessages(m, pid) do _sendMessages(m, pid, "Blah") end
  defp _sendMessages(0, pid, _) do
    send pid, {:shutdown}
  end
  defp _sendMessages(m, pid, msg) do
    send pid, {:ok, msg}
    _sendMessages(m-1,pid,msg)
  end
end

Star.run(4,1)
