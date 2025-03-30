package cn.jian.stback.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class QrUtil {

	private static final int BLACK = 0xFF000000;

	private static final int WHITE = 0xFFFFFFFF;

	static String path = "/data/file/";

	private static void mergeImg(File file1, BufferedImage img2, File file) throws Exception {
		BufferedImage img1 = (BufferedImage) ImageIO.read(file1);
		int w1 = img1.getWidth();
		int h1 = img1.getHeight();
		int h2 = img2.getHeight();
		int w2 = img2.getWidth();
		System.out.println(w1);
		System.out.println(h1);
		System.out.println(w2);
		System.out.println(h2);
		Graphics2D graphics2D = null;
		// 生成新图片
		BufferedImage destImage = null;
		destImage = new BufferedImage(w1, h1, BufferedImage.TYPE_INT_RGB);
		graphics2D = destImage.createGraphics();
		// 插入的位置坐标需要手动尝试
		graphics2D.drawImage(img1, 0, 0, w1, h1, null);
		graphics2D.drawImage(img2, 516, 766, w2, h2, null);
		graphics2D.dispose();
		// 保存图片
		file.createNewFile();
		ImageIO.write(destImage, "png", file);

	}

//	public static void main(String[] args) throws Exception {
//
//		createQrCode("https://66manage.org/mobile/#/register?id=5n3nyae3", "D://file/test.png");
//		mergeImg(new File("/data/file/invite.png"), new File("/data/file/test.png"));
//		System.out.println("合成图片成功！");
//
//	}

	public static void createInviteCode(String id) throws Exception {
		File file = new File(path + id + ".png");
		if (!file.exists()) {
			BufferedImage img = createQrCode("https://66manage.org/mobile/#/register?id=" + id);
			mergeImg(new File("/data/file/invite.png"), img, file);
		}

	}
	
	

	public static BufferedImage createQrCode(String url) throws Exception {
		Map<EncodeHintType, String> hints = new HashMap<>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, 300, 300, hints);
		return deleteWhite(bitMatrix);
	}

	public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
		BufferedImage image = deleteWhite(matrix); // 去白边的话加这一行
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format " + format + " to " + file);
		}
	}

	// 去白边的话，调用这个方法
	private static BufferedImage deleteWhite(BitMatrix matrix) {
		int[] rec = matrix.getEnclosingRectangle();
		int resWidth = rec[2] + 1;
		int resHeight = rec[3] + 1;

		BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
		resMatrix.clear();
		for (int i = 0; i < resWidth; i++) {
			for (int j = 0; j < resHeight; j++) {
				if (matrix.get(i + rec[0], j + rec[1]))
					resMatrix.set(i, j);
			}
		}

		int width = resMatrix.getWidth();
		int height = resMatrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, resMatrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

}
