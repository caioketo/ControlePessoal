﻿using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace ControlePessoalBE.Models
{
    public class ItemDeCompraModel
    {
        [Key]
        public int ItemDeCompraId { get; set; }

        [ForeignKey("Compra")]
        [Column(Order = 2)]
        public int? CompraID { get; set; }
        public virtual CompraModel Compra { get; set; }
        [ForeignKey("Preco")]
        [Column(Order = 3)]
        public int? PrecoID { get; set; }
        public virtual PrecoModel Preco { get; set; }
        [ForeignKey("Produto")]
        [Column(Order = 4)]
        public int? ProdutoID { get; set; }
        public virtual ProdutoModel Produto { get; set; }

        public ItemDeCompraModel()
        {
        }
    }
}