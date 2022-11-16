package com.myasinmanager.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myasinmanager.exception.ProductInfoNotFoundException;
import com.myasinmanager.model.ProductEntity;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductSPAPIService {

	@Autowired
	private ProductService productService;

	static List<ProductEntity> productsInSpApi;
	static {
		ProductEntity p1 = ProductEntity.builder()
				.image("https://resources.sears.com.mx/medios-plazavip/fotos/productos_sears1/original/3301639.jpg")
				.title("Tarro Cerveza Berna Vidrio 270Ml Trans Cerve").buyCost(new BigDecimal(149.0))
				.additionalCost(BigDecimal.ZERO)
				.supplierLink("https://www.sears.com.mx/producto/873141/tarro-cerveza-berna-vidrio-270ml-trans-cerve/")
				.asin("B0892PYYBW").category("House").tags("Christmas").notes("Expensive product")
				.currentBSR(new BigDecimal(10)).currentBBPrice(new BigDecimal(149.0)).date(new Date())
				.fbaSellerCount(10).roi(new BigDecimal(10.0)).netMargin(new BigDecimal(2.0)).build();

		ProductEntity p2 = ProductEntity.builder()
				.image("https://resources.sears.com.mx/imgsplaza-sears/sears/?tp=p&id=832608&t=original")
				.title("Jarra con Vidrio con Tapa de Metal Jv97230 Good & Good").buyCost(new BigDecimal(449.0))
				.additionalCost(BigDecimal.ZERO)
				.supplierLink(
						"https://www.sears.com.mx/producto/751388/jarra-con-vidrio-con-tapa-de-metal-jv97230-good-good/")
				.asin("B0872PYYBS").category("House").tags("Christmas").notes("Expensive product")
				.currentBSR(new BigDecimal(10)).currentBBPrice(new BigDecimal(449.0)).date(new Date()).fbaSellerCount(2)
				.roi(new BigDecimal(7.0)).netMargin(new BigDecimal(5.0)).build();

		ProductEntity p3 = ProductEntity.builder()
				.image("https://resources.sears.com.mx/medios-plazavip/fotos/productos_sears1/original/3506445.jpg")
				.title("\n" + "Tarro Metalizado de Cerámica Mickey Adulto 20").buyCost(new BigDecimal(169.0))
				.additionalCost(BigDecimal.ZERO)
				.supplierLink(
						"https://www.sears.com.mx/producto/2167334/tarro-metalizado-de-ceramica-mickey-adulto-20-oz-fund-kids/")
				.asin("B1892PYYB1").category("House").tags("Gifts").notes("Expensive product")
				.currentBSR(new BigDecimal(129)).currentBBPrice(new BigDecimal(169.0)).date(new Date())
				.fbaSellerCount(123).roi(new BigDecimal(10.0)).netMargin(new BigDecimal(2.0)).build();

		ProductEntity p4 = ProductEntity.builder()
				.image("https://resources.sears.com.mx/medios-plazavip/fotos/productos_sears1/original/3433150.jpg")
				.title("Ps5 Mlb The Show 22").buyCost(new BigDecimal(1799.0)).additionalCost(BigDecimal.ZERO)
				.supplierLink("https://www.sears.com.mx/producto/2096137/ps5-mlb-the-show-22/").asin("B0892PYYBS")
				.category("House").tags("Videogames").notes("Expensive product").currentBSR(new BigDecimal(1))
				.currentBBPrice(new BigDecimal(1799.0)).date(new Date()).fbaSellerCount(28).roi(new BigDecimal(100.0))
				.netMargin(new BigDecimal(24.0)).build();

		ProductEntity p5 = ProductEntity.builder()
				.image("https://resources.sears.com.mx/medios-plazavip/fotos/productos_sears1/original/3589578.jpg")
				.title("Figura de Lujo Black Adam 12").buyCost(new BigDecimal(1049.0)).additionalCost(BigDecimal.ZERO)
				.supplierLink("https://www.sears.com.mx/producto/2247115/figura-de-lujo-black-adam-12/")
				.asin("B0832PYYBS").category("House").tags("Christmas").notes("Expensive product")
				.currentBSR(new BigDecimal(8)).currentBBPrice(new BigDecimal(1049.0)).date(new Date())
				.fbaSellerCount(10).roi(new BigDecimal(17.0)).netMargin(new BigDecimal(2.0)).build();

		ProductEntity p6 = ProductEntity.builder()
				.image("https://resources.sears.com.mx/medios-plazavip/fotos/productos_sears1/original/3328428.jpg")
				.title("Celular Motorola Edge 20 5G Xt2143-1 Color Negro R9 (Telcel)").buyCost(new BigDecimal(9999.0))
				.additionalCost(BigDecimal.ZERO)
				.supplierLink(
						"https://www.sears.com.mx/producto/1141788/celular-motorola-edge-20-5g-xt2143-1-color-negro-r9-telcel/")
				.asin("B0892PYYFS").category("Phones").tags("New").notes("This has discount")
				.currentBSR(new BigDecimal(10)).currentBBPrice(new BigDecimal(9999.0)).date(new Date())
				.fbaSellerCount(4).roi(new BigDecimal(145.0)).netMargin(new BigDecimal(27.0)).build();

		ProductEntity p7 = ProductEntity.builder()
				.image("https://resources.sears.com.mx/medios-plazavip/fotos/productos_sears1/original/3436455.jpg")
				.title("Playera Manga Corta Carter´s Modelo 3N125310 para Niña").buyCost(new BigDecimal(239.0))
				.additionalCost(BigDecimal.ZERO)
				.supplierLink(
						"https://www.sears.com.mx/producto/2082513/playera-manga-corta-carter-s-modelo-3n125310-para-nina/")
				.asin("A0892PYYBS").category("Girls").tags("Beautiful").notes("For beautiful girls")
				.currentBSR(new BigDecimal(10)).currentBBPrice(new BigDecimal(149.0)).date(new Date())
				.fbaSellerCount(60).roi(new BigDecimal(32.0)).netMargin(new BigDecimal(23.0)).build();

		ProductEntity p8 = ProductEntity.builder()
				.image("https://resources.sears.com.mx/medios-plazavip/fotos/productos_sears1/original/2902550.jpg")
				.title("Reloj para Hombre Bulova Color Dorado").buyCost(new BigDecimal(8239.0))
				.additionalCost(BigDecimal.ZERO)
				.supplierLink("https://www.sears.com.mx/producto/164594/reloj-para-hombre-bulova-color-dorado/")
				.asin("B1892PYYBS").category("House").tags("Christmas").notes("Expensive product")
				.currentBSR(new BigDecimal(11)).currentBBPrice(new BigDecimal(8239.0)).date(new Date())
				.fbaSellerCount(2).roi(new BigDecimal(10.0)).netMargin(new BigDecimal(2.0)).build();

		ProductEntity p9 = ProductEntity.builder()
				.image("https://resources.sears.com.mx/medios-plazavip/fotos/productos_sears1/original/3618938.jpg")
				.title("Ajedrez 3D Coleccionable Star Wars Novelty").buyCost(new BigDecimal(1999.0))
				.additionalCost(BigDecimal.ZERO)
				.supplierLink("https://www.sears.com.mx/producto/2297176/ajedrez-3d-coleccionable-star-wars-novelty/")
				.asin("B0882PYYBS").category("Games").tags("Games").notes("Perfect gift").currentBSR(new BigDecimal(90))
				.currentBBPrice(new BigDecimal(1999.0)).date(new Date()).fbaSellerCount(10).roi(new BigDecimal(10.0))
				.netMargin(new BigDecimal(2.0)).build();

		ProductEntity p10 = ProductEntity.builder()
				.image("https://resources.sears.com.mx/medios-plazavip/fotos/productos_sears1/original/3564266.jpg")
				.title("Arbol de Navidad Kensingtone Fir con 350 Luces Led 2.13 M").buyCost(new BigDecimal(10999.0))
				.additionalCost(BigDecimal.ZERO)
				.supplierLink(
						"https://www.sears.com.mx/producto/2234061/arbol-de-navidad-kensingtone-fir-con-350-luces-led-2-13-m/")
				.asin("B0892PYYBR").category("House").tags("Christmas").notes("Expensive product")
				.currentBSR(new BigDecimal(35)).currentBBPrice(new BigDecimal(10999.0)).date(new Date())
				.fbaSellerCount(1).roi(new BigDecimal(45.0)).netMargin(new BigDecimal(680.0)).build();

		productsInSpApi = List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
	}

	public ProductEntity findByAsin(String asin) {
		log.debug("Searching in SP by asin {}", asin);
		return productsInSpApi.stream().filter(p -> asin.equals(p.getAsin())).findFirst()
				.orElseThrow(() -> new ProductInfoNotFoundException(asin));
	}

	public void processByAsin(String asin) {
		log.debug("Product to be processed: {}", asin);
		ProductEntity productFromSPAPI = findByAsin(asin);

		productService.create(productFromSPAPI);

	}
}