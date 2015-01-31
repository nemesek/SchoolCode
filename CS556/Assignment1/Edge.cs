using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Assignment1
{
    public class Edge<T>
    {
        public Vertex<T> Source { get; set; }
        public Vertex<T> Destination { get; set; }

        public T Label { get; set; }
    }
}
