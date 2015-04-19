defmodule MS do
  def start(n) do
    spawn(MS,:_start,[5])
  end
  def _start(n) do
     pids = _run(n,[])
     _setupCallback(pids)
  end

  def to_slave(msg,n, master) do
    IO.puts "Got message #{msg}"
    send master, {n,msg}
  end

  defp _setupCallback(pids) do
    receive do
      {p, msg} ->
        IO.puts "Got Internal message #{msg}"
        pid = Enum.at(pids,p-1)
        send pid,{msg}
        _setupCallback(pids)
      end
    IO.puts "Done setting them up"
  end

  defp _run(0,pids) do pids end
  defp _run(n, pids) do
    Process.flag(:trap_exit,true)
    pid = spawn_link(MS,:slave,[])
    _run(n-1,[pid|pids])
  end

  def slave do
    receive do
      {msg} -> IO.puts "Processed: #{msg}"
      slave
    end
  end
end

# pid = spawn(MS,:start,[5])
# send pid, {2,"Hello"}
