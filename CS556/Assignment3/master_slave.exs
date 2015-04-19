defmodule MS do
  def start(n) do
    spawn(MS,:_start,[5])
  end
  def _start(n) do
     pids = _run(n,[])
     _setupCallback(pids)
  end

  def to_slave(msg,n, master) do
    IO.puts "Got message #{msg} relaying to slave #{n}"
    send master, {n,msg}
  end

  defp _setupCallback(pids) do
    receive do
      {p, "die"} ->
        IO.puts "Routing die message"
        pid = Enum.at(pids,p-1)
        send pid,{:shutdown}
        newPid = pid = spawn_link(MS,:slave,[])
        newPids = List.replace_at(pids,p-1,newPid)
        IO.puts "Initialized new process for N = #{p}"
        _setupCallback(newPids)
      {p, msg} ->
        IO.puts "Router sending message #{msg}"
        pid = Enum.at(pids,p-1)
        send pid,{msg}
        _setupCallback(pids)
      end
  end

  defp _run(0,pids) do pids end
  defp _run(n, pids) do
    Process.flag(:trap_exit,true)
    pid = spawn_link(MS,:slave,[])
    _run(n-1,[pid|pids])
  end

  def slave do
    receive do
      {:shutdown} -> exit(:normal)
      {msg} -> IO.puts "Slave Processed: #{msg}"
      slave
    end
  end
end

# pid = spawn(MS,:start,[5])
# send pid, {2,"Hello"}
