using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ControlePessoalBE.Models.Json
{
    public class ProdutoJson
    {
        public int ProdutoId { get; set; }
        public string Descricao { get; set; }
        public double Quantidade { get; set; }
        public double QuantidadeAviso { get; set; }
        public virtual List<PrecoJson> Precos { get; set; }
        public virtual List<CodigoJson> Codigos { get; set; }

        public ProdutoJson()
        {
            Precos = new List<PrecoJson>();
            Codigos = new List<CodigoJson>();
        }

        public ProdutoJson(ProdutoModel produtoB)
        {
            if (produtoB == null)
            {
                Precos = new List<PrecoJson>();
                Codigos = new List<CodigoJson>();
            }
            else
            {
                Precos = new List<PrecoJson>();
                Codigos = new List<CodigoJson>();
                this.ProdutoId = produtoB.ProdutoId;
                this.Descricao = produtoB.Descricao;
                this.Quantidade = produtoB.Quantidade;
                this.QuantidadeAviso = produtoB.QuantidadeAviso;
                foreach (PrecoModel preco in produtoB.Precos)
                {
                    this.Precos.Add(new PrecoJson(preco));
                }
                foreach (CodigoModel codigo in produtoB.Codigos)
                {
                    this.Codigos.Add(new CodigoJson(codigo));
                }
            }
        }
    }
}