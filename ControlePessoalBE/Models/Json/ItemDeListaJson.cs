using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ControlePessoalBE.Models.Json
{
    public class ItemDeListaJson
    {
        public int ItemDeListaId { get; set; }
        public ProdutoJson Produto { get; set; }

        public ItemDeListaJson()
        {
        }

        public ItemDeListaJson(ItemDeListaModel item)
        {
            if (item != null)
            {
                this.ItemDeListaId = item.ItemDeListaId;
                this.Produto = new ProdutoJson(item.Produto);
            }
        }
    }
}