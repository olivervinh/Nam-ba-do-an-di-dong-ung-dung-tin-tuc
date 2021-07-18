using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using API_News.Data;
using API_News.Models;

namespace API_News.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ArticlesController : ControllerBase
    {
        private readonly DPContext _context;

        public ArticlesController(DPContext context)
        {
            _context = context;
        }
        [HttpGet("laytheoloai/{id}")]
        public async Task<ActionResult<IEnumerable<Article>>> GetArticlesCategory(int id)
        {
            return await _context.Articles.Where(s => s.Status == 1 &&s.IdCategory==id).ToListAsync();
        }
        // GET: api/Articles
        [HttpGet("laytheodieukien")]
        public async Task<ActionResult<IEnumerable<Article>>> GetArticlesWhere()
        {
            return await _context.Articles.Where(s=>s.Status==1).ToListAsync();
        }
        // GET: api/Articles
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Article>>> GetArticles()
        {
            return await _context.Articles.ToListAsync();
        }
        [HttpPost("search")]
        public async Task<ActionResult<ICollection<Article>>> SearchArticleAsync([FromForm]string search)
        {
            return await _context.Articles.Where(s => s.Description.Contains(search)||s.Title.Contains(search)).ToListAsync();
        }

       // GET: api/Articles/5
       [HttpGet("{id}")]
        public async Task<ActionResult<Article>> GetArticle(int id)
        {
            var article = await _context.Articles.FindAsync(id);

            if (article == null)
            {
                return NotFound();
            }

            return article;
        }

        // PUT: api/Articles/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        //[HttpPut("like/{id}")]
        //public async Task<IActionResult> PutArticleLike(int id, [FromForm] string test)
        //{
        //    Article article1;
        //    article1 = await _context.Articles.FindAsync(id);
        //    if(article1.LikeArticle == 0)
        //    {
        //        article1.LikeArticle = 1;
        //        _context.Articles.Update(article1);

        //        try
        //        {
        //            await _context.SaveChangesAsync();
        //        }
        //        catch (DbUpdateConcurrencyException)
        //        {
        //            if (!ArticleExists(id))
        //            {
        //                return NotFound();
        //            }
        //            else
        //            {
        //                throw;
        //            }
        //        }
        //        return Ok();
        //    }
        //    else
        //    {
        //        article1.LikeArticle = 0;
        //        _context.Articles.Update(article1);

        //        try
        //        {
        //            await _context.SaveChangesAsync();
        //        }
        //        catch (DbUpdateConcurrencyException)
        //        {
        //            if (!ArticleExists(id))
        //            {
        //                return NotFound();
        //            }
        //            else
        //            {
        //                throw;
        //            }
        //        }
        //        return null;
        //    }
        //}



        //[HttpPut("save/{id}")]
        //public async Task<IActionResult> PutArticleSave(int id, [FromForm] string test)
        //{
        //    Article article1;
        //    article1 = await _context.Articles.FindAsync(id);
        //    if (article1.SaveArticle == 0)
        //    {
        //        article1.SaveArticle = 1;
        //        _context.Articles.Update(article1);

        //        try
        //        {
        //            await _context.SaveChangesAsync();
        //        }
        //        catch (DbUpdateConcurrencyException)
        //        {
        //            if (!ArticleExists(id))
        //            {
        //                return NotFound();
        //            }
        //            else
        //            {
        //                throw;
        //            }
        //        }
        //        return Ok();
        //    }
        //    else
        //    {
        //        article1.SaveArticle = 0;
        //        _context.Articles.Update(article1);

        //        try
        //        {
        //            await _context.SaveChangesAsync();
        //        }
        //        catch (DbUpdateConcurrencyException)
        //        {
        //            if (!ArticleExists(id))
        //            {
        //                return NotFound();
        //            }
        //            else
        //            {
        //                throw;
        //            }
        //        }
        //        return null;
        //    }

           

           
        //}
        // POST: api/Articles
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<Article>> PostArticle(Article article)
        {
            _context.Articles.Add(article);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetArticle", new { id = article.Id }, article);
        }

        // DELETE: api/Articles/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteArticle(int id)
        {
            var article = await _context.Articles.FindAsync(id);
            if (article == null)
            {
                return NotFound();
            }

            _context.Articles.Remove(article);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool ArticleExists(int id)
        {
            return _context.Articles.Any(e => e.Id == id);
        }

        [HttpGet("tintuclienquan1/{id}")]
        public async Task<ActionResult<ICollection<Article>>> tintuclienquan1(int id)
        {
            //lay ra tin tuc cung loai
            Article article1 = await _context.Articles.FindAsync(id);
            return await _context.Articles.Where(s=>s.IdCategory==article1.IdCategory).ToArrayAsync();
        }
        //[HttpGet("laydanhsachthich")]
        //public async Task<ActionResult<IEnumerable<Article>>> danhsachthich()
        //{
        //    return await _context.Articles.Where(s => s.LikeArticle == 1).ToListAsync();
        //}
        //[HttpGet("laydanhsachluu")]
        //public async Task<ActionResult<IEnumerable<Article>>> danhsachluu()
        //{
        //    return await _context.Articles.Where(s => s.SaveArticle == 1).ToListAsync();
        //}

    }
}
