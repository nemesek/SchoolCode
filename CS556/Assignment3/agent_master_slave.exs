defmodule MS do
  def start(n) do
    Agent.start_link(fn -> %{} end)
  end

  defp _run(0,pids) do pids end
  defp _run(n, pids) do
    Process.flag(:trap_exit,true)
    pid = spawn_link(MS,:slave,[])
    _run(n-1,[pid|pids])
  end
end
