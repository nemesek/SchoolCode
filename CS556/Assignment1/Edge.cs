namespace Assignment1
{
    public class Edge<T1, T2,T3 >
    {
        public Vertex<T2,T3> Source { get; set; }
        public Vertex<T2,T3> Destination { get; set; }

        public T1 Label { get; set; }
    }
}
