using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Assignment1
{
    public class Graph<T>
    {
        private List<Vertex<T>> _list = new List<Vertex<T>>();
        public Graph()
        {

        }

        public Graph<T> AddVertex(Vertex<T> vertex, T label)
        {
            vertex.Label = label;
            _list.Add(vertex);
            return this;
        }
    }
}
