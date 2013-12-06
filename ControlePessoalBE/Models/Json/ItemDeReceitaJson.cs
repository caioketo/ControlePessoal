using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ControlePessoalBE.Models.Json
{
    public class ItemDeReceitaJson
    {
        public int ItemDeReceitaId { get; set; }
        public virtual ProdutoJson Produto { get; set; }
        public double QuantidadeUtilizada { get; set; }

        public ItemDeReceitaJson(ItemDeReceitaModel item)
        {
            if (item != null)
            {
                this.ItemDeReceitaId = item.ItemDeReceitaId;
                this.Produto = new ProdutoJson(item.Produto);
                this.QuantidadeUtilizada = item.QuantidadeUtilizada;
            }
        }
    }
}