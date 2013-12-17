using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ControlePessoalBE.Models.Json
{
    public class ListaJson
    {
        public int ListaId { get; set; }
        public bool Aberta { get; set; }

        public virtual List<ItemDeListaJson> Itens { get; set; }

        public ListaJson()
        {
            Itens = new List<ItemDeListaJson>();
        }

        public ListaJson(ListaModel lista)
        {
            Itens = new List<ItemDeListaJson>();
            if (lista != null)
            {
                this.Aberta = lista.Aberta;
                this.ListaId = lista.ListaId;
                foreach (ItemDeListaModel item in lista.Itens)
                {
                    this.Itens.Add(new ItemDeListaJson(item));
                }
            }
        }
    }
}