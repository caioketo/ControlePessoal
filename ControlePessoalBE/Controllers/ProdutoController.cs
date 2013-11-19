using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using ControlePessoalBE.Models;

namespace ControlePessoalBE.Controllers
{
    public class ProdutoController : Controller
    {
        private CPContext db = new CPContext();

        //
        // GET: /Produto/

        public ActionResult Index()
        {
            return View(db.Produtos.ToList());
        }

        //
        // GET: /Produto/Details/5

        public ActionResult Details(int id = 0)
        {
            ProdutoModel produtomodel = db.Produtos.Find(id);
            if (produtomodel == null)
            {
                return HttpNotFound();
            }
            return View(produtomodel);
        }

        //
        // GET: /Produto/Create

        public ActionResult Create()
        {
            return View();
        }

        //
        // POST: /Produto/Create

        [HttpPost]
        public ActionResult Create(ProdutoModel produtomodel)
        {
            if (ModelState.IsValid)
            {
                db.Produtos.Add(produtomodel);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            return View(produtomodel);
        }

        public JsonResult AddCodigo(int produtoId, string codigo)
        {
            ProdutoModel produto = db.Produtos.Where(p => p.ProdutoId == produtoId).First();
            if (produto != null)
            {
                CodigoModel codigoM = new CodigoModel();
                codigoM.Cadastro = DateTime.Now;
                codigoM.ProdutoID = produtoId;
                codigoM.Codigo = codigo;
                codigoM = db.Codigos.Add(codigoM);
                db.SaveChanges();
                produto.Codigos.Add(codigoM);
                db.Entry(produto).State = EntityState.Modified;
                db.SaveChanges();
            }
            return Json(produto, JsonRequestBehavior.AllowGet);
        }


        public JsonResult Produtos()
        {
            return Json(db.Produtos.ToList(), JsonRequestBehavior.AllowGet);
        }

        public JsonResult FindCodigo(string codigo)
        {
            return Json(db.Produtos.Where(p => p.ProdutoId == db.Codigos.Where(c => c.Codigo.Equals(codigo)).FirstOrDefault().ProdutoID).First(), JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public JsonResult CreateProd(string descricao, double quantidade, double quantidadeaviso)
        {
            ProdutoModel produto = new ProdutoModel();
            produto.Descricao = descricao;
            produto.Quantidade = quantidade;
            produto.QuantidadeAviso = quantidadeaviso;
            produto = db.Produtos.Add(produto);
            db.SaveChanges();
            return Json(produto, JsonRequestBehavior.AllowGet); 
        }

        //
        // GET: /Produto/Edit/5

        public ActionResult Edit(int id = 0)
        {
            ProdutoModel produtomodel = db.Produtos.Find(id);
            if (produtomodel == null)
            {
                return HttpNotFound();
            }
            return View(produtomodel);
        }

        //
        // POST: /Produto/Edit/5

        [HttpPost]
        public ActionResult Edit(ProdutoModel produtomodel)
        {
            if (ModelState.IsValid)
            {
                db.Entry(produtomodel).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(produtomodel);
        }

        //
        // GET: /Produto/Delete/5

        public ActionResult Delete(int id = 0)
        {
            ProdutoModel produtomodel = db.Produtos.Find(id);
            if (produtomodel == null)
            {
                return HttpNotFound();
            }
            return View(produtomodel);
        }

        //
        // POST: /Produto/Delete/5

        [HttpPost, ActionName("Delete")]
        public ActionResult DeleteConfirmed(int id)
        {
            ProdutoModel produtomodel = db.Produtos.Find(id);
            db.Produtos.Remove(produtomodel);
            db.SaveChanges();
            return RedirectToAction("Index");
        }

        protected override void Dispose(bool disposing)
        {
            db.Dispose();
            base.Dispose(disposing);
        }
    }
}