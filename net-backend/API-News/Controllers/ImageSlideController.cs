using API_News.Data;
using API_News.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace API_News.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ImageSlideController : ControllerBase
    {
        private readonly DPContext _context;
        public ImageSlideController(DPContext context )
        {
            this._context = context;
        }
        [HttpGet]
        public async Task<ActionResult<IEnumerable<ImageSlide>>> ImageSlides()
        {
            return await _context.ImageSlides.ToListAsync();
        }
    }
}
