namespace Assignment1
{
    public class Edge<T>
    {
        public Vertex<T> Source { get; set; }
        public Vertex<T> Destination { get; set; }

        public T Label { get; set; }
    }
}
