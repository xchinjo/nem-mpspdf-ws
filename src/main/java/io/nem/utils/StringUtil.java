package io.nem.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {

	public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	public static SimpleDateFormat simpleDateFormatHHMMSS = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	public static SimpleDateFormat simpleOnlyDateFormat = new SimpleDateFormat("dd/MM/yyyy");
	public static SimpleDateFormat simpleOnlyMMDD = new SimpleDateFormat("dd/MM");
	public static SimpleDateFormat DDMMAAAA = new SimpleDateFormat("ddMMyyyy");
	public static NumberFormat nf = NumberFormat.getInstance();
	public static final DecimalFormat df = new DecimalFormat("#,##0");

	protected static Logger logger = LoggerFactory.getLogger(StringUtil.class);

	public StringUtil() {

	}
	
	
	public static final boolean isBlankOrNull(Object o) {
		if(null == o) {
			return true;
		}
		
		if("".equals(o.toString())) {
			return true;
		}
		
		return false;
	}
	
	public static final String sanityzer(String s) {
		if(!isBlankOrNull(s)) {
			s = s.replace("-", "");
			s = s.trim();
		}
		
		return s;
	}
	
	
	/**
	 * 
	 * @param num
	 * @return
	 */
	public static String format(long num) {
		DecimalFormat formatter = new DecimalFormat("###.######");
		formatter.setMinimumFractionDigits(6);
		BigDecimal bd = new BigDecimal(num);
		bd = bd.divide(BigDecimal.TEN.pow(6));
		String retorno = (formatter.format(bd));
		return retorno.replace(",", ".");		
	}	

	public static String limit(String valor, int limit) {
		if (valor != null) {

			if (valor.length() <= limit) {
				return valor;
			} else {
				return valor.substring(0, limit - 1);
			}

		}

		return null;
	}

	/**
	 * Verifica se a String � igual utilizando "equalsIgnoreCase" e "trim"
	 * 
	 * @param s
	 * @param s2
	 * @return
	 */
	public static boolean compare(String s, String s2) {
		return s.trim().equalsIgnoreCase(s2.trim());
	}

	public static boolean isFolderID(String str) {
		if (StringUtil.isVoid(str)) {
			return false;
		}
		boolean result = str != null && !str.trim().equalsIgnoreCase("") && !str.equalsIgnoreCase("null");

		return result && str.toUpperCase().lastIndexOf("P") > -1;
	}

	public static long normalizeIdPasta(String str) {
		return new Long(str.replace("P", "")).longValue();
	}

	public static int getPorcento(int atual, int total) {
		nf.setMaximumFractionDigits(0);
		return Integer.parseInt(nf.format(((double) atual / (double) total) * 100));
	}

	/**
	 * Remove todo tipo de acentuacoes
	 * 
	 * @param string
	 * @return
	 */
	public static String removeAcentos(String string) {
		if (string == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder(string);
		for (int i = 0; i < sb.length(); i++) {
			if (sb.charAt(i) == '�' || sb.charAt(i) == '�' || sb.charAt(i) == '�' || sb.charAt(i) == '�'
					|| sb.charAt(i) == '�') {
				sb.setCharAt(i, 'a');
			} else if (sb.charAt(i) == '�' || sb.charAt(i) == '�' || sb.charAt(i) == '�' || sb.charAt(i) == '�') {
				sb.setCharAt(i, 'e');
			} else if (sb.charAt(i) == '�' || sb.charAt(i) == '�' || sb.charAt(i) == '�' || sb.charAt(i) == '�') {
				sb.setCharAt(i, 'i');
			} else if (sb.charAt(i) == '�' || sb.charAt(i) == '�' || sb.charAt(i) == '�' || sb.charAt(i) == '�'
					|| sb.charAt(i) == '�') {
				sb.setCharAt(i, 'o');
			} else if (sb.charAt(i) == '�' || sb.charAt(i) == '�' || sb.charAt(i) == '�' || sb.charAt(i) == '�') {
				sb.setCharAt(i, 'u');
			} else if (sb.charAt(i) == '�') {
				sb.setCharAt(i, 'c');
			} else if (sb.charAt(i) == '�' || sb.charAt(i) == '�' || sb.charAt(i) == '�' || sb.charAt(i) == '�'
					|| sb.charAt(i) == '�') {
				sb.setCharAt(i, 'A');
			} else if (sb.charAt(i) == '�' || sb.charAt(i) == '�' || sb.charAt(i) == '�' || sb.charAt(i) == '�') {
				sb.setCharAt(i, 'E');
			} else if (sb.charAt(i) == '�' || sb.charAt(i) == '�' || sb.charAt(i) == '�' || sb.charAt(i) == '�') {
				sb.setCharAt(i, 'I');
			} else if (sb.charAt(i) == '�' || sb.charAt(i) == '�' || sb.charAt(i) == '�' || sb.charAt(i) == '�'
					|| sb.charAt(i) == '�') {
				sb.setCharAt(i, 'O');
			} else if (sb.charAt(i) == '�' || sb.charAt(i) == '�' || sb.charAt(i) == '�' || sb.charAt(i) == '�') {
				sb.setCharAt(i, 'U');
			} else if (sb.charAt(i) == '�') {
				sb.setCharAt(i, 'C');
			}
		}

		return sb.toString();
	}

	private static final char[] FIRST_CHAR = (" !'#$%&'()*+\\-./0123456789:;<->?@ABCDEFGHIJKLMNOPQRSTUVWXYZ"
			+ "[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~ E,f'.++^%S<O Z  ''''.-"
			+ "-~Ts>o ZY !C#$Y|$'(a<--(_o+23'u .,1o>113?AAAAAAACEEEEIIIIDNOO"
			+ "OOOXOUUUUyTsaaaaaaaceeeeiiiidnooooo/ouuuuyty").toCharArray();

	/** Para a normaliza��o dos caracteres de 32 a 255, segundo caracter */
	private static final char[] SECOND_CHAR = ("  '         ,                                               "
			+ "\\                                   $  r'. + o  E      ''  "
			+ "  M  e     #  =  'C.<  R .-..     ..>424     E E            "
			+ "   E E     hs    e e         h     e e     h ").toCharArray();

	public static String normalize(String str) {

		char[] chars = str.toCharArray();
		StringBuilder ret = new StringBuilder(chars.length * 2);
		for (int i = 0; i < chars.length; ++i) {
			char aChar = chars[i];

			// excecao com a virgula nao acha o indice no array de char
			if (chars[i] == ',') {
				ret.append(',');
				continue;
			}
			if (aChar == ' ' || aChar == '\t') {
				ret.append(' '); // convertido para espa�o
			} else if (aChar == '/' || aChar == '\\') {
				ret.append('_'); // convertido para espa�o
			} else if (aChar > ' ' && aChar < 256) {
				if (FIRST_CHAR[aChar - ' '] != ' ') {
					ret.append(FIRST_CHAR[aChar - ' ']); // 1 caracter
				}
				if (SECOND_CHAR[aChar - ' '] != ' ') {
					ret.append(SECOND_CHAR[aChar - ' ']); // 2 caracteres
				}
			}
		}

		return ret.toString();
	}

	public static void main(String[] args) {
		String text = "223.877.508-05.5";

		String text2 = normalize(text);
		System.out.println(text2);

		text2 = removeCaracteresInvalidos(text);
		System.out.println(text2);

	}

	public static String normalizeApostrofe(String s) {
		if (isVoid(s)) {
			return "";
		}
		return s.replaceAll("'", "");
	}

	public static String normalizaTexto(final String str) {
		String strSemAcentos = Normalizer.normalize(str, Normalizer.Form.NFD);
		strSemAcentos = strSemAcentos.replaceAll("[^\\p{ASCII}]", "");
		return strSemAcentos;
	}

	public static String normalizeToCsv(String s) {

		if (isVoid(s)) {
			return " ";
		}
		String to = new String(s);

		to = to.replaceAll("\"", "\"\"");
		to = "\"" + to + "\"";

		to = removeAcentos(to);
		// to = replaceByConf(to);
		to = removeQuebraLinha(to);

		return to.toUpperCase().trim();
	}

	public static String removeQuebraLinha(String s) {
		s = s.replaceAll("\r", "");
		return s.replaceAll("\n", "");
	}

	/**
	 * Substitui as letras que possui acentos para sem acento.
	 * 
	 * @param valor
	 * @return
	 */
	public static String replaceAcentos(String valor) {
		valor = valor.replaceAll("�", "a");
		valor = valor.replaceAll("�", "a");
		valor = valor.replaceAll("�", "a");
		valor = valor.replaceAll("�", "a");
		valor = valor.replaceAll("�", "e");
		valor = valor.replaceAll("�", "e");
		valor = valor.replaceAll("�", "e");
		valor = valor.replaceAll("�", "n");
		valor = valor.replaceAll("�", "i");
		valor = valor.replaceAll("�", "o");
		valor = valor.replaceAll("�", "o");
		valor = valor.replaceAll("�", "o");
		valor = valor.replaceAll("�", "o");
		valor = valor.replaceAll("�", "u");
		valor = valor.replaceAll("�", "u");
		valor = valor.replaceAll("�", "u");
		valor = valor.replaceAll("�", "c");

		valor = valor.replaceAll("�", "A");
		valor = valor.replaceAll("�", "A");
		valor = valor.replaceAll("�", "A");
		valor = valor.replaceAll("�", "A");
		valor = valor.replaceAll("�", "E");
		valor = valor.replaceAll("�", "E");
		valor = valor.replaceAll("�", "E");
		valor = valor.replaceAll("�", "I");
		valor = valor.replaceAll("�", "O");
		valor = valor.replaceAll("�", "O");
		valor = valor.replaceAll("�", "O");
		valor = valor.replaceAll("�", "O");
		valor = valor.replaceAll("�", "U");
		valor = valor.replaceAll("�", "U");
		valor = valor.replaceAll("�", "U");
		valor = valor.replaceAll("�", "C");
		valor = valor.replaceAll("�", "N");
		valor = valor.replaceAll(" ", "_");

		valor = cleanString(valor);

		return valor;
	}

	public static String cleanString(String toRep) {

		if (toRep == null || toRep.length() <= 0) {
			return toRep;
		}

		String temp = "";

		for (int i = 0; i < toRep.length(); i++) {
			Character c = new Character(toRep.charAt(i));
			if (!Character.isHighSurrogate(c)) {
				temp += "_";
			} else {
				temp += c;
			}

		}

		return temp;

	}

	/**
	 * Metodo responsavel em p�r pontos de retic�ncia somente em textos sem
	 * espa�o, n�o permitindo exprimir o texto completamente.
	 * 
	 * @param txt
	 *            (texto a ser formatado)
	 * @param limitCarac
	 *            (quantidade de caracteres a serem exibidos)
	 * @return
	 */
	public static String formatReticencia(String txt, int limitCarac) {
		if (txt == null)
			return null;

		if (txt.length() > limitCarac) {
			if (txt.indexOf(' ') < 0) { // verifica se nao contem espa�os
				txt = txt.substring(0, (limitCarac - 3)).concat("...");
			}
		}
		return txt;
	}

	/**
	 * Metodo responsavel em idenficar em destaque a palavra especifica em um
	 * texto na palavra encontrada.
	 * 
	 * @param texto
	 *            ( texto a ser modificado por highlith)
	 * @param palavra
	 *            ( palavra a ser encontrada no <b>texto</b>)
	 * @param formatIni
	 *            ( formato da <b>palavra</b> encontrada ex: <span ....>
	 * @param formatEnd
	 *            ( formato html fechamento da tag do formatIni ex: </span...>
	 * @return ( texto formatado com highligh
	 */
	public static String highlight(String texto, String palavra, String formatIni, String formatEnd) {
		String textoModifier = texto.toUpperCase().trim();
		String[] palavraUp = palavra.toUpperCase().trim().split(" ");
		for (int i = 0; i < palavraUp.length; i++) {
			textoModifier = textoModifier.replaceAll(palavraUp[i], formatIni + palavraUp[i] + formatEnd);
		}

		return textoModifier;
	}

	public static boolean isVoid(String s) {

		return (s == null || s.trim().length() <= 0 || "null".equalsIgnoreCase(s.trim()));

	}

	public static boolean isVoid(Object s) {

		return (s == null || s.toString() == null || s.toString().trim().length() <= 0
				|| "null".equalsIgnoreCase(s.toString().trim()));

	}

	public static boolean isVoid(Object[] s) {

		return (s == null || s.length <= 0);

	}

	public static boolean isVoid(Collection s) {

		return (s == null || s.isEmpty());

	}

	/**
	 * 
	 * @param time
	 * @return
	 */
	public static String getFormatedDate(long time) {

		try {
			return simpleDateFormat.format(new Date(time));
		} catch (Exception e) {
			return "*Data inv�lida*";
		}

	}
	
	/**
	 * 
	 * @param time
	 * @return
	 */
	
	
	 public static Date removeTime(Date date) {
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        cal.set(Calendar.HOUR_OF_DAY, 0);
	        cal.set(Calendar.MINUTE, 0);
	        cal.set(Calendar.SECOND, 0);
	        cal.set(Calendar.MILLISECOND, 0);
	        return cal.getTime();
	    }

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String getFormatedDateObject(Object date) {
		Date odate = null;

		if (date == null) {
			return null;
		}

		if (date instanceof java.util.Date) {
			odate = (Date) date;
		} else {
			return null;
		}

		try {
			return simpleOnlyDateFormat.format(odate);
		} catch (Exception e) {
			return "*Data inv�lida*";
		}

	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String getFormatedDDMMObject(Object date) {
		Date odate = null;

		if (date == null) {
			return null;
		}

		if (date instanceof java.util.Date) {
			odate = (Date) date;
		} else {
			return null;
		}

		try {
			return simpleOnlyMMDD.format(odate);
		} catch (Exception e) {
			return "*Data inv�lida*";
		}

	}
	
	public static String getFormatedDDMMAAAA(Date odate) {
		if (odate == null) {
			return null;
		}

		try {
			return DDMMAAAA.format(odate);
		} catch (Exception e) {
			return "*Data inv�lida*";
		}

	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String getFormatedDate(Date date) {

		try {
			return simpleDateFormat.format(date);
		} catch (Exception e) {
			return "*Data inv�lida*";
		}

	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String getFormatedDateHHMMSS(Date date) {
		try {
			return simpleDateFormatHHMMSS.format(date);
		} catch (Exception e) {
			return "*Data inv�lida*";
		}
	}

	/**
	 * 
	 * @param date
	 * @param onlyDate
	 * @return
	 */
	public static String getFormatedDate(Date date, boolean onlyDate) {

		try {
			if (onlyDate) {
				return simpleOnlyDateFormat.format(date);
			} else {

				return simpleDateFormat.format(date);
			}
		} catch (Exception e) {
			return "*Data inv�lida*";
		}

	}

	public static Date parseDate(String date) {

		try {
			return simpleDateFormat.parse(date);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public static Date parseDate(String date, String format) {

		try {
			return new SimpleDateFormat(format).parse(date);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}

	}

	public static String removeUltimoCaracter(String s) {
		if (s.length() > 0) {
			return s.substring(0, s.length() - 1);
		}
		return "";
	}

	public static String removeUltimaPalavra(String s) {
		if (isVoid(s)) {
			return "";
		}
		s = s.trim();
		while (!existeEspacoBrancoNoFim(s)) {
			s = removeUltimoCaracter(s);
		}
		return s;
	}

	public static boolean existeEspacoBrancoNoFim(String s) {
		return s.substring(s.length() - 1, s.length()).equals(" ");
	}

	public static String getResumoTexto(String text, String wordsSearch, int size) {
		if (text == null || wordsSearch == null || size <= 0) {
			return "";
		}

		int posFirst = getFirstOcurrenceString(text, wordsSearch);
		int textSize = text.length();

		String textReturn = "";

		if (textSize < size) {
			return text;
		}

		if ((posFirst + size) > textSize) {
			int diff = (posFirst + size) - textSize;
			if (posFirst - diff < 0) {
				textReturn = text;
			} else {
				textReturn = text.substring(posFirst - diff, textSize);
			}
		} else {
			textReturn = text.substring(posFirst, posFirst + size);
		}

		return textReturn;
	}

	public static String[] splitQuery(String wordSearch) {
		String strSplit[] = wordSearch.split(" AND ");
		if (strSplit.length <= 0) {
			strSplit = wordSearch.split(" OR ");
		}
		return strSplit;
	}

	/**
	 * 
	 * @param text
	 * @param wordSearch
	 * @return
	 */
	public static int getFirstOcurrenceString(String text, String wordSearch) {
		if (text == null || wordSearch == null) {
			return 0;
		}
		String strSplit[] = splitQuery(wordSearch);
		int posFirst = 0;
		for (int i = 0; i < strSplit.length; i++) {
			int pos = text.toUpperCase().indexOf(strSplit[i].toUpperCase());
			if (pos > -1 && (posFirst == 0 || pos < posFirst)) {
				posFirst = pos;
			}
		}
		return posFirst;
	}

	/**
	 * 
	 * @param s
	 * @param formato
	 * @return
	 */
	public static Date convertStringToDate(String s, String formato) {
		Date retorno = null;

		if (null != s) {
			SimpleDateFormat sdf = new SimpleDateFormat(formato);
			try {
				retorno = sdf.parse(s);
			} catch (ParseException e) {
				logger.error(e.getMessage());
			}
		}

		return retorno;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isBlankOrNull(String value) {
		boolean retorno = false;

		if (value == null) {
			return true;
		}

		if ("".equals(value)) {
			return true;
		}

		return retorno;
	}

	public static boolean isBlankZeroOrNull(String value) {
		boolean retorno = false;

		if (value == null) {
			return true;
		}

		if ("".equals(value)) {
			return true;
		}

		if ("".equals(value.trim())) {
			return true;
		}

		if ("0".equals(value)) {
			return true;
		}

		return retorno;
	}

	public static boolean isBlankOrNull(Integer value) {
		boolean retorno = false;

		if (value == null) {
			return true;
		}

		if ("".equals(value)) {
			return true;
		}

		if (0 == (value.intValue())) {
			return true;
		}

		return retorno;
	}

	public static boolean isBlankOrNull(Long value) {
		boolean retorno = false;

		if (value == null) {
			return true;
		}

		if ("".equals(value)) {
			return true;
		}

		if (0 == (value.intValue())) {
			return true;
		}

		return retorno;
	}

	public static boolean isBlankOrNull(Date value) {
		boolean retorno = false;

		if (value == null) {
			return true;
		}

		return retorno;
	}

	/**
	 * 
	 * @param o
	 * @return
	 */
	public static Long getLong(Object o) {
		if (o == null) {
			return new Long(0);
		}

		if (o instanceof Integer) {
			return new Long((Integer) o);
		}

		if (o instanceof String) {
			return Long.parseLong((String) o);
		}
		
		if (o instanceof BigInteger) {
			try {
				return ((BigInteger) o).longValue();
			} catch (Exception e) {
				return 0l;
			}
		}

		if (o instanceof BigDecimal) {
			try {
				return ((BigDecimal) o).longValue();
			} catch (Exception e) {
				return 0l;
			}
		}

		return null;
	}

	/**
	 * 
	 * @param o
	 * @return
	 */
	public static BigInteger getBigInteger(Object o) {
		if (o == null) {
			return BigInteger.ZERO;
		}

		if (o instanceof BigInteger) {
			try {
				return ((BigInteger) o);
			} catch (Exception e) {
				return BigInteger.ZERO;
			}
		}
		return BigInteger.ZERO;
	}

	/**
	 * 
	 * @param o
	 * @return
	 */
	public static Integer getInteger(Object o) {
		if (o == null) {
			return new Integer(0);
		}

		if (o instanceof Integer) {
			return ((Integer) o);
		}

		if (o instanceof Long) {
			return ((Long) o).intValue();
		}

		if (o instanceof String) {
			try {
				return Integer.parseInt((String) o);
			} catch (Exception e) {
				return 0;
			}
		}
		
		if (o instanceof Boolean) {
			try {
				return ((Boolean) o) ? 1 : 0;
			} catch (Exception e) {
				return 0;
			}
		}

		if (o instanceof BigInteger) {
			try {
				return ((BigInteger) o).intValue();
			} catch (Exception e) {
				return 0;
			}
		}

		if (o instanceof BigDecimal) {
			try {
				return ((BigDecimal) o).intValue();
			} catch (Exception e) {
				return 0;
			}
		}

		return null;
	}

	/**
	 * 
	 * @param object
	 * @return
	 */
	public static Date getDate(Object object) {

		if (object == null) {
			return null;
		}

		if (object instanceof Timestamp) {
			Timestamp stamp = (Timestamp) (object);
			Date date = new Date(stamp.getTime());
			return date;
		}

		if (object instanceof Date) {
			Date date = (Date) (object);
			return date;
		}

		if (object instanceof String) {
			return stringToDate((String) object);
		}

		return null;
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public static Date stringToDate(String s) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
			return sdf.parse(s);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date calculaDias(int i) {
		Date hoje = new Date();

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(hoje);
		calendar.add(Calendar.DAY_OF_MONTH, i);

		return calendar.getTime();
	}
	
	/**
	 * 
	 * @param i
	 * @return
	 */
	public static Date calculaAnos(int i) {
		Date hoje = new Date();

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(hoje);
		calendar.add(Calendar.YEAR, i);

		return calendar.getTime();
	}	

	/**
	 * Obtem o primeiro dia do mes
	 * 
	 * @return
	 */
	public static Date primeiroDiaDoMes() {
		Calendar primeiroDia = Calendar.getInstance();
		primeiroDia.set(Calendar.DAY_OF_MONTH, 1);
		return primeiroDia.getTime();
	}
	
	/**
	 * Obtem o ultimo dia do mes
	 * 
	 * @return
	 */
	public static Date ultimoDiaDoMes() {
		Calendar dataDeHoje = Calendar.getInstance();
		
		Calendar ultimoDia = Calendar.getInstance();
		ultimoDia.set(Calendar.DAY_OF_MONTH, 1);
		ultimoDia.add(Calendar.MONTH, dataDeHoje.get(Calendar.MONTH + 1));
		ultimoDia.add(Calendar.DAY_OF_MONTH, -1);
		return ultimoDia.getTime();
	}


	public static String removeCaracteresInvalidos(String ret) {
		// Remove todos . da string
		ret = ret.replaceAll("\\.?", "");
		// Remove todas , da string
		ret = ret.replaceAll(",?", "");
		// Remove todas - da string
		ret = ret.replaceAll("-?", "");
		// Remove todas : da string
		ret = ret.replaceAll(":?", "");
		// Remove todas ( da string
		ret = ret.replaceAll("\\(?", "");
		// Remove todas ) da string
		ret = ret.replaceAll("\\)?", "");
		// Remove todas � da string
		ret = ret.replaceAll("�?", "");
		// Remove todas | da string
		ret = ret.replaceAll("\\|?", "");
		// Remove todas \ da string
		ret = ret.replaceAll("\\\\?", "");

		return ret;
	}

	/**
	 * 
	 * @return
	 */
	public static Date hoje() {
		return new Date();
	}

	/**
	 * Cria um caminho com base na data passada, exemplo 2016-01-25 Ficaria
	 * 2016/01/25
	 * 
	 * @param hoje
	 * @return
	 */
	public static String criarCaminhoArquivoPorData(Date data) {
		String retorno = null;
		String ano, mes, dia;
		final String pathSeparator = File.separator;

		SimpleDateFormat dd = new SimpleDateFormat("dd");
		SimpleDateFormat mm = new SimpleDateFormat("MM");
		SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");

		ano = yyyy.format(data);
		mes = mm.format(data);
		dia = dd.format(data);

		retorno = ano + pathSeparator + mes + pathSeparator + dia + pathSeparator;

		return retorno;
	}

	/**
	 * 
	 * @param tipo
	 * @param valor
	 * @return
	 * @throws ParseException
	 */
	public static String getDatePart(String tipo, String valor) throws ParseException {
		String retorno = "";
		String[] data = valor.split("/");

		if (data.length == 3) {
			retorno = StringUtil.organizaNomesDePastas(tipo, data);
		} else if (data.length == 2) {
			retorno = StringUtil.organizaNomesDePastasMesAno(tipo, data);
		}

		return retorno;
	}

	/**
	 * M�todo responsavel por organizar o nome das pastas na arvore de
	 * documentos
	 * 
	 * JAN/2015 = Na arvore fica Janeiro 01/2015 = Na arvore fica Janeiro
	 * 
	 * 01/02/2015 = Na arvore fica Fevereiro
	 * 
	 */
	public static String organizaNomesDePastas(String tipo, String[] data) {
		String retorno = "";
		try {
			switch (tipo) {
			case "yyyy":
				// retorno = String.valueOf(data.getYear());
				retorno = data[2].trim();
				break;
			case "MM":
				// retorno = String.valueOf(data.getMonth());
				retorno = data[1].trim();
				break;
			case "dd":
				// retorno = String.valueOf(data.getDay());
				retorno = data[0].trim();
				break;
			case "mes":
				// switch (data.getMonth())
				String s = data[1].trim();
				try {

					final Integer m = Integer.parseInt(data[1].trim());
					s = String.valueOf(m);
				} catch (Exception n) {
				}

				switch (s) {
				case "1":
					retorno = "JANEIRO";
					break;
				case "2":
					retorno = "FEVEREIRO";
					break;
				case "3":
					retorno = "MAR�O";
					break;
				case "4":
					retorno = "ABRIL";
					break;
				case "5":
					retorno = "MAIO";
					break;
				case "6":
					retorno = "JUNHO";
					break;
				case "7":
					retorno = "JULHO";
					break;
				case "8":
					retorno = "AGOSTO";
					break;
				case "9":
					retorno = "SETEMBRO";
					break;
				case "10":
					retorno = "OUTUBRO";
					break;
				case "11":
					retorno = "NOVEMBRO";
					break;
				case "12":
					retorno = "DEZEMBRO";
					break;
				case "JAN":
					retorno = "JANEIRO";
					break;
				case "FEV":
					retorno = "FEVEREIRO";
					break;
				case "MAR":
					retorno = "MAR�O";
					break;
				case "ABR":
					retorno = "ABRIL";
					break;
				case "MAI":
					retorno = "MAIO";
					break;
				case "JUN":
					retorno = "JUNHO";
					break;
				case "JUL":
					retorno = "JULHO";
					break;
				case "AGO":
					retorno = "AGOSTO";
					break;
				case "SET":
					retorno = "SETEMBRO";
					break;
				case "OUT":
					retorno = "OUTUBRO";
					break;
				case "NOV":
					retorno = "NOVEMBRO";
					break;
				case "DEZ":
					retorno = "DEZEMBRO";
					break;
				default:
					retorno = data[1].trim();
					break;
				}
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retorno;
	}

	/**
	 * JAN/2015
	 * 
	 * @param tipo
	 * @param data
	 * @return
	 */
	public static String organizaNomesDePastasMesAno(String tipo, String[] data) {
		String retorno = "";
		try {
			switch (tipo) {
			case "yyyy":
				// retorno = String.valueOf(data.getYear());
				retorno = data[1].trim();
				break;
			case "MM":
				// retorno = String.valueOf(data.getMonth());
				retorno = data[0].trim();
				break;
			case "mes":
				// switch (data.getMonth())
				String s = data[0].trim();
				try {

					final Integer m = Integer.parseInt(data[1].trim());
					s = String.valueOf(m);
				} catch (Exception n) {
				}

				switch (s) {
				case "1":
					retorno = "JANEIRO";
					break;
				case "2":
					retorno = "FEVEREIRO";
					break;
				case "3":
					retorno = "MAR�O";
					break;
				case "4":
					retorno = "ABRIL";
					break;
				case "5":
					retorno = "MAIO";
					break;
				case "6":
					retorno = "JUNHO";
					break;
				case "7":
					retorno = "JULHO";
					break;
				case "8":
					retorno = "AGOSTO";
					break;
				case "9":
					retorno = "SETEMBRO";
					break;
				case "10":
					retorno = "OUTUBRO";
					break;
				case "11":
					retorno = "NOVEMBRO";
					break;
				case "12":
					retorno = "DEZEMBRO";
					break;
				case "JAN":
					retorno = "JANEIRO";
					break;
				case "FEV":
					retorno = "FEVEREIRO";
					break;
				case "MAR":
					retorno = "MAR�O";
					break;
				case "ABR":
					retorno = "ABRIL";
					break;
				case "MAI":
					retorno = "MAIO";
					break;
				case "JUN":
					retorno = "JUNHO";
					break;
				case "JUL":
					retorno = "JULHO";
					break;
				case "AGO":
					retorno = "AGOSTO";
					break;
				case "SET":
					retorno = "SETEMBRO";
					break;
				case "OUT":
					retorno = "OUTUBRO";
					break;
				case "NOV":
					retorno = "NOVEMBRO";
					break;
				case "DEZ":
					retorno = "DEZEMBRO";
					break;
				default:
					retorno = data[0].trim();
					break;
				}
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retorno;

	}

	public static String getIp() {
		InetAddress thisIp;
		String ip = null;
		try {
			thisIp = InetAddress.getLocalHost();
			if (thisIp == null) {
				logger.error("Nao pegou o IPHost da M�quina ");
				return "";
			}
			ip = thisIp.getHostAddress();

		} catch (UnknownHostException e) {
			logger.error(e.getMessage());
		}
		return ip;
	}

	public static Timestamp hojeDataHoraTimestamp() {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		return time;
	}

	/**
	 * Gera um texto aleat�rio
	 * 
	 * @param minTextSize
	 * @param maxTextSize
	 * @param uppercaseLetters
	 * @param lowercaseLetters
	 * @param digits
	 * @return
	 */
	public static String generateRandomText(int minTextSize, int maxTextSize, boolean uppercaseLetters,
			boolean lowercaseLetters, boolean digits) {
		final int FIRST_UPPERCASE_LETTER = 'A';
		final int FIRST_LOWERCASE_LETTER = 'a';
		final int NUMBER_OF_LETTERS_IN_ALPHABET = 26;

		Random rnd = new Random();

		int textSize = minTextSize + rnd.nextInt(maxTextSize);
		if (textSize > maxTextSize) {
			textSize = maxTextSize;
		}

		int currentLength = 0;

		StringBuilder text = new StringBuilder(textSize);
		ArrayList<Object> textList = new ArrayList<Object>(textSize);

		if (digits) {
			int noOfDigits = 0;
			if (uppercaseLetters == false && lowercaseLetters == false) {
				noOfDigits = textSize - currentLength;
			} else {
				noOfDigits = rnd.nextInt(textSize);
			}
			for (int i = 0; i < noOfDigits; i++) {
				textList.add(new Integer(rnd.nextInt(9)));
			}
			currentLength = noOfDigits;
		}

		if (uppercaseLetters) {
			int noOfUpper = 0;
			if (lowercaseLetters == false) {
				noOfUpper = textSize - currentLength;
			} else {
				noOfUpper = rnd.nextInt(textSize - currentLength);
			}

			for (int i = 0; i < noOfUpper; i++) {
				textList.add(
						new Character((char) (FIRST_UPPERCASE_LETTER + rnd.nextInt(NUMBER_OF_LETTERS_IN_ALPHABET))));
			}
			currentLength += noOfUpper;
		}

		if (lowercaseLetters) {
			int noOfLower = textSize - (currentLength);
			for (int i = 0; i < noOfLower; i++)
				textList.add(
						new Character((char) (FIRST_LOWERCASE_LETTER + rnd.nextInt(NUMBER_OF_LETTERS_IN_ALPHABET))));
		}

		Collections.shuffle(textList);

		for (Object value : textList) {
			text.append(value);
		}

		return text.toString();
	}

	/**
	 * Verifica se a string � um n�mero.
	 * 
	 * @param valor
	 * @return
	 */
	public static boolean isNumeric(String valor) {
		if (valor != null) {
			for (int i = 0; i < valor.length(); i++) {
				if (!Character.isDigit(valor.charAt(i))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Valida CPF
	 * 
	 * @param strCpf
	 * @return
	 */
	public static boolean isCPF(String strCpf) {
		int d1, d2;
		int digito1, digito2, resto;
		int digitoCPF;
		String nDigResult;

		d1 = d2 = 0;
		digito1 = digito2 = resto = 0;

		for (int nCount = 1; nCount < strCpf.length() - 1; nCount++) {
			digitoCPF = Integer.valueOf(strCpf.substring(nCount - 1, nCount)).intValue();

			// multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4
			// e assim por diante.
			d1 = d1 + (11 - nCount) * digitoCPF;

			// para o segundo digito repita o procedimento incluindo o primeiro
			// digito calculado no passo anterior.
			d2 = d2 + (12 - nCount) * digitoCPF;
		}
		;

		// Primeiro resto da divis�o por 11.
		resto = (d1 % 11);

		// Se o resultado for 0 ou 1 o digito � 0 caso contr�rio o digito � 11
		// menos o resultado anterior.
		if (resto < 2)
			digito1 = 0;
		else
			digito1 = 11 - resto;

		d2 += 2 * digito1;

		// Segundo resto da divis�o por 11.
		resto = (d2 % 11);

		// Se o resultado for 0 ou 1 o digito � 0 caso contr�rio o digito � 11
		// menos o resultado anterior.
		if (resto < 2)
			digito2 = 0;
		else
			digito2 = 11 - resto;

		// Digito verificador do CPF que est� sendo validado.
		String nDigVerific = strCpf.substring(strCpf.length() - 2, strCpf.length());

		// Concatenando o primeiro resto com o segundo.
		nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

		// comparar o digito verificador do cpf com o primeiro resto + o segundo
		// resto.
		return nDigVerific.equals(nDigResult);
	}

	public static String getRGDigit(String rg) {
		String ret;
		int sum = 0;
		int j = 2;

		for (int i = rg.length(); i > 0; i--) {
			sum += (Integer.parseInt(rg.substring(i - 1, i)) * j);
			j++;
			if (j > 10) {
				j = 2;
			}
		}

		sum = (sum % 11);

		if (sum == 10) {
			ret = "X";
		} else {
			ret = sum + "";
		}

		return ret;
	}

	public static String removeAcentuacao(String texto) {
		String newTexto = texto;
		newTexto = newTexto.replace('�', 'a');
		newTexto = newTexto.replace('�', 'a');
		newTexto = newTexto.replace('�', 'a');
		newTexto = newTexto.replace('�', 'a');
		newTexto = newTexto.replace('�', 'e');
		newTexto = newTexto.replace('�', 'e');
		newTexto = newTexto.replace('�', 'i');
		newTexto = newTexto.replace('�', 'o');
		newTexto = newTexto.replace('�', 'o');
		newTexto = newTexto.replace('�', 'o');
		newTexto = newTexto.replace('�', 'u');
		newTexto = newTexto.replace('�', 'u');
		newTexto = newTexto.replace('�', 'c');
		newTexto = newTexto.replace('�', 'y');

		newTexto = newTexto.replace('�', 'A');
		newTexto = newTexto.replace('�', 'A');
		newTexto = newTexto.replace('�', 'A');
		newTexto = newTexto.replace('�', 'A');
		newTexto = newTexto.replace('�', 'A');
		newTexto = newTexto.replace('�', 'E');
		newTexto = newTexto.replace('�', 'E');
		newTexto = newTexto.replace('�', 'I');
		newTexto = newTexto.replace('�', 'O');
		newTexto = newTexto.replace('�', 'O');
		newTexto = newTexto.replace('�', 'O');
		newTexto = newTexto.replace('�', 'U');
		newTexto = newTexto.replace('�', 'U');
		newTexto = newTexto.replace('�', 'C');
		newTexto = newTexto.replace('�', 'Y');
		texto = newTexto;

		return texto;
	}

	/**
	 * Pega somente o nome do arquivo no caminho
	 * 
	 * @param nameFile
	 * @return
	 */
	public static String getOnlyName(String nameFile) {
		String newName = "";
		if (nameFile.lastIndexOf("\\") > -1) {
			newName = nameFile.substring(nameFile.lastIndexOf("\\") + 1, nameFile.length());
		} else if (nameFile.lastIndexOf("/") > -1) {
			newName = nameFile.substring(nameFile.lastIndexOf("/") + 1, nameFile.length());
		} else {
			newName = nameFile;
		}

		return newName;
	}

	/**
	 * Feature [SDM 63478] Erro ao exportar resultados da Busca R�pida Content
	 * PDG-GOLDFARB<BR>
	 * M�todo respons�vel por obter os campos de um ResultSet <BR>
	 * 
	 * @since 25/02/2014 11:12:23
	 * @author Renato Vieira<BR>
	 */
	public static ArrayList<String> obtemCamposResultSet(ResultSet rs) throws Exception {

		ArrayList<String> result = new ArrayList<String>();

		ResultSetMetaData rsmd = rs.getMetaData();

		int numColumns = rsmd.getColumnCount();

		for (int i = 1; i < numColumns + 1; i++) {

			String columnName = rsmd.getColumnName(i);

			result.add(columnName);
		}

		return result;
	}

	public static String getString(Object object) {

		if (object == null) {
			return null;
		}

		if (object instanceof String) {
			return (String) object;
		}

		return object.toString();
	}

	/**
	 * retorno o booleano do objeto.
	 * 
	 * @param object
	 * @return
	 */
	public static boolean getBoolean(Object object) {
		Integer i = getInteger(object);

		if (i != null) {
			return i.intValue() != 0;
		}

		return false;
	}

	public static String retiraEspacoCaracteresEspeciais(String stringFonte) {
		String passa = stringFonte;
		passa = passa.replaceAll("~", "");
		passa = passa.replaceAll("�", "");
		passa = passa.replaceAll("--", "");
		passa = passa.replaceAll("�", "");
		passa = passa.replaceAll("\\|", "");
		passa = passa.replaceAll("\\\\", "");
		passa = passa.replaceAll("[+=*&%$#@!_]", "");
		passa = passa.replaceAll("['\"]", "");
		passa = passa.replaceAll("[<>()\\{\\}]", "");
		passa = passa.replaceAll("['\\\\.,()|/]", "");
		passa = passa.replaceAll("^\\s+", "");
		passa = passa.replaceAll("\\s+$", "");
		return passa;
	}

	/**
	 * 
	 * @param str
	 * @param i
	 * @return
	 */
	public static String quebraString(String str, int i) {
		if (isBlankOrNull(str)) {
			return null;
		}

		if (str.length() > i) {
			return str.substring(0, i);
		}

		return str;
	}

	/**
	 * 
	 * @param idUsuario
	 * @return
	 */
	public static String desencriptar(String s) {
		return new String(Base64.getDecoder().decode(s));
	}
	public static String encriptar(String s) {
		return Base64.getEncoder().encodeToString(s.getBytes());
	}

	public static boolean isBlankZeroOrNull(Long idStatus) {
		if(idStatus == null){
			return true;
		}
		return idStatus.intValue() == 0;
	}
	

	public static <T> void copyAllFields(T to, T from) {
		Class<T> clazz = (Class<T>) from.getClass();
		// OR:
		// Class<T> clazz = (Class<T>) to.getClass();
		List<Field> fields = getAllModelFields(clazz);

		if (fields != null) {
			for (Field field : fields) {
				try {
					field.setAccessible(true);
					field.set(to, field.get(from));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * @param aClass
	 * @return
	 */
	public static List<Field> getAllModelFields(Class aClass) {
		List<Field> fields = new ArrayList<>();
		do {
			Collections.addAll(fields, aClass.getDeclaredFields());
			aClass = aClass.getSuperclass();
		} while (aClass != null);
		return fields;
	}
	
	/**
	 * 
	 * @param cnpj
	 * @return
	 */
	public static boolean isCnpjValido(String cnpj) {
        if (!cnpj.substring(0, 1).equals("")) {
            try {
                cnpj = cnpj.replace('.', ' ');//onde h� ponto coloca espa�o
                cnpj = cnpj.replace('/', ' ');//onde h� barra coloca espa�o
                cnpj = cnpj.replace('-', ' ');//onde h� tra�o coloca espa�o
                cnpj = cnpj.replaceAll(" ", "");//retira espa�o
                int soma = 0, dig;
                String cnpj_calc = cnpj.substring(0, 12);
                if (cnpj.length() != 14) {
                    return false;
                }
                char[] chr_cnpj = cnpj.toCharArray();
                /* Primeira parte */
                for (int i = 0; i < 4; i++) {
                    if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                        soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
                    }
                }
                for (int i = 0; i < 8; i++) {
                    if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9) {
                        soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
                    }
                }
                dig = 11 - (soma % 11);
                cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(
                        dig);
                /* Segunda parte */
                soma = 0;
                for (int i = 0; i < 5; i++) {
                    if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                        soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
                    }
                }
                for (int i = 0; i < 8; i++) {
                    if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9) {
                        soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
                    }
                }
                dig = 11 - (soma % 11);
                cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(
                        dig);
                return cnpj.equals(cnpj_calc);
            }
            catch (Exception e) {
                return false;
            }
        }
        else {
            return false;
        }
    }
	
	/**
	 * 
	 * @param ie
	 * @return
	 */
	private static String removeMascara(String ie){
		String strIE = "";
		for(int i = 0; i < ie.length(); i++){
			if (Character.isDigit(ie.charAt(i))){
				strIE += ie.charAt(i);
			}
		}
		return strIE;
	}

	/**
	 * 
	 * @param inscricaoEstadual
	 * @param siglaUf
	 * @return
	 */
	public static boolean validaIE(String inscricaoEstadual, String siglaUf) {
		boolean retorno = true;
		try{
			String strIE = removeMascara(inscricaoEstadual);
			siglaUf = siglaUf.toUpperCase();
			if(siglaUf.equals("AC")){
				validaIEAcre(strIE);
			} else if(siglaUf.equals("AL")){
				validaIEAlagoas(strIE);
			} else if(siglaUf.equals("AP")){
				validaIEAmapa(strIE);
			} else if(siglaUf.equals("AM")){
				validaIEAmazonas(strIE);
			} else if(siglaUf.equals("BA")){
				validaIEBahia(strIE);
			} else if(siglaUf.equals("CE")){
				validaIECeara(strIE);
			} else if(siglaUf.equals("ES")){
				validaIEEspiritoSanto(strIE);
			} else if(siglaUf.equals("GO")){
				validaIEGoias(strIE);
			} else if(siglaUf.equals("MA")){
				validaIEMaranhao(strIE);
			} else if(siglaUf.equals("MT")){
				validaIEMatoGrosso(strIE);
			} else if(siglaUf.equals("MS")){
				validaIEMatoGrossoSul(strIE);
			} else if(siglaUf.equals("MG")){
				validaIEMinasGerais(strIE);
			} else if(siglaUf.equals("PA")){
				validaIEPara(strIE);
			} else if(siglaUf.equals("PB")){
				validaIEParaiba(strIE);
			} else if(siglaUf.equals("PR")){
				validaIEParana(strIE);
			} else if(siglaUf.equals("PE")){
				validaIEPernambuco(strIE);
			} else if(siglaUf.equals("PI")){
				validaIEPiaui(strIE);
			} else if(siglaUf.equals("RJ")){
				validaIERioJaneiro(strIE);
			} else if(siglaUf.equals("RN")){
				validaIERioGrandeNorte(strIE);
			} else if(siglaUf.equals("RS")){
				validaIERioGrandeSul(strIE);
			} else if(siglaUf.equals("RO")){
				validaIERondonia(strIE);
			} else if(siglaUf.equals("RR")){
				validaIERoraima(strIE);
			} else if(siglaUf.equals("SC")){
				validaIESantaCatarina(strIE);
			} else if(siglaUf.equals("SP")){
				if(inscricaoEstadual.charAt(0) == 'P'){
					strIE = "P" + strIE;
				}
				validaIESaoPaulo(strIE);
			} else if(siglaUf.equals("SE")){
				validaIESergipe(strIE);
			} else if(siglaUf.equals("TO")){
				validaIETocantins(strIE);
			} else if(siglaUf.equals("DF")){
				validaIEDistritoFederal(strIE);
			} else {
				throw new Exception("Estado n�o encontrado : " + siglaUf);
			}
		}catch(Exception e){
			return false;
		}
		
		return retorno;
	}

	/**
	 * Valida inscri&#65533;&#65533;o estadual do estado do Acre
	 * @param ie (Inscri&#65533;&#65533;o estadual)
	 * @throws Exception 
	 */
	private static void validaIEAcre(String ie) throws Exception { //inic&#65533;o do validaIEAcre()
		//valida a quantidade de d&#65533;gitos
		if (ie.length() != 13){
			throw new Exception("Quantidade de digitos inv�lida.");
		}

		//valida os dois primeiros digitos - devem ser iguais a 01
		for(int i = 0; i < 2; i++){
			if (Integer.parseInt(String.valueOf(ie.charAt(i))) != i){
				throw new Exception("Inscri��o Estadual inv�lida");
			}
		}

		int soma = 0;
		int pesoInicial = 4;
		int pesoFinal = 9;
		int d1 = 0; //primeiro digito verificador
		int d2 = 0; //segundo digito verificador

		//calcula o primeiro digito
		for(int i = 0; i < ie.length() - 2; i++){
			if (i < 3){
				soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicial;
				pesoInicial--;
			} 
			else {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFinal;
				pesoFinal--;
			}
		}
		d1 = 11 - (soma % 11);
		if (d1 == 10 || d1 == 11){
			d1 = 0;
		}

		//calcula o segundo digito
		soma = d1 * 2;
		pesoInicial = 5;
		pesoFinal = 9;
		for(int i = 0; i < ie.length() - 2; i++){
			if (i < 4){
				soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicial;
				pesoInicial--;
			} 
			else {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFinal;
				pesoFinal--;
			}
		}

		d2 = 11 - (soma % 11);
		if (d2 == 10 || d2 == 11){
			d2 = 0;
		}

		//valida os digitos verificadores
		String dv = d1 + "" + d2;
		if (!dv.equals(ie.substring(ie.length() - 2, ie.length()))){
			throw new Exception("Digito verificador inv�lido.");
		}
	} //fim do validaIEAcre()

	/**
	 * Valida inscri&#65533;&#65533;o estadual do estado do Alagoas
	 * @param ie (Inscri&#65533;&#65533;o estadual)
	 * @throws Exception 
	 */
	private static void validaIEAlagoas(String ie) throws Exception{
		//valida quantidade de d&#65533;gitos
		if (ie.length() != 9){
			throw new Exception("Quantidade de d&#65533;gitos inv&#65533;lida.");
		}

		//valida os dois primeiros d&#65533;gitos - deve ser iguais a 24
		if (!ie.substring(0, 2).equals("24")){
			throw new Exception("Inscri��o estadual inv�lida.");
		}

		//valida o terceiro d&#65533;gito - deve ser 0,3,5,7,8
		int[] digits = {0,3,5,7,8};
		boolean check = false;
		for(int i = 0; i < digits.length; i++){
			if (Integer.parseInt(String.valueOf(ie.charAt(2))) == digits[i]){
				check = true;
				break;
			}
		}
		if (!check){
			throw new Exception("Inscri��o estadual inv�lida.");
		}

		//calcula o d&#65533;gito verificador
		int soma = 0;
		int peso = 9;
		int d = 0; //d&#65533;gito verificador
		for(int i = 0; i < ie.length() - 1; i++){
			soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
			peso--;
		}
		d = ((soma * 10)%11);
		if (d == 10){
			d = 0;
		}

		//valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
			throw new Exception("Digito verificador inv�lido.");
		}
	}

	/**
	 * Valida inscri&#65533;&#65533;o estadual do estado do Amap&#65533;
	 * @param ie (Inscri&#65533;&#65533;o estadual)
	 * @throws Exception 
	 */
	private static void validaIEAmapa(String ie) throws Exception{
		//valida quantida de d&#65533;gitos
		if (ie.length() != 9){
			throw new Exception("Quantidade de digitos inv�lidas.");
		}

		//verifica os dois primeiros d&#65533;gitos - deve ser igual 03
		if (!ie.substring(0, 2).equals("03")){
			throw new Exception("Inscri��o estadual inv�lida.");
		}

		//calcula o d&#65533;gito verificador
		int d1 = -1;
		int soma = -1;
		int peso = 9;

		//configura o valor do d&#65533;gito verificador e da soma de acordo com faixa das inscri&#65533;&#65533;es
		long x = Long.parseLong(ie.substring(0, ie.length() -1)); //x = inscri&#65533;&#65533;o estadual sem o d&#65533;gito verificador
		if (x >= 3017001L && x <= 3019022L){
			d1 = 1;
			soma = 9;
		} else if (x >= 3000001L && x <= 3017000L){
			d1 = 0;
			soma = 5;
		} else if (x >= 3019023L){
			d1 = 0;
			soma = 0;
		}

		for(int i = 0; i < ie.length() - 1; i++){
			soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
			peso--;
		}

		int d = 11 - ((soma % 11)); //d = armazena o d&#65533;gito verificador ap&#65533;s c&#65533;lculo
		if (d == 10){
			d = 0;
		} else if (d == 11){
			d = d1;
		}

		//valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
			throw new Exception("Digito verificador inv�lido.");
		}
	}

	/**
	 * Valida inscri&#65533;&#65533;o estadual do estado do Amazonas
	 * @param ie (Inscri&#65533;&#65533;o estadual)
	 * @throws Exception 
	 */
	private static void validaIEAmazonas(String ie) throws Exception{
		//valida quantida de d&#65533;gitos
		if (ie.length() != 9){
			throw new Exception("Quantidade de digitos inv�lidas.");
		}

		int soma = 0;
		int peso = 9;
		int d = -1; //d&#65533;gito verificador
		for (int i = 0; i < ie.length() - 1; i++){
			soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
			peso--;
		}

		if (soma < 11){
			d = 11 - soma;
		} else if ((soma % 11) <= 1){
			d = 0;
		} else {
			d = 11 - (soma % 11);
		}

		//valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
			throw new Exception("Digito verificador inv�lido.");
		}
	}

	/**
	 * Valida inscri&#65533;&#65533;o estadual do estado do Bahia
	 * @param ie (Inscri&#65533;&#65533;o estadual)
	 * @throws Exception 
	 */
	private static void validaIEBahia(String ie) throws Exception{
		//valida quantida de d&#65533;gitos
		if (ie.length() != 8  && ie.length() != 9){
			throw new Exception("Quantidade de digitos inv�lidas." + ie);
		}

		//C&#65533;lculo do m&#65533;dulo de acordo com o primeiro d&#65533;gito da inscri&#65533;&#65533;o Estadual
		int  modulo = 10;
		int firstDigit = Integer.parseInt(String.valueOf(ie.charAt(ie.length()==8 ? 0 : 1 ))) ; 
		if(firstDigit == 6 || firstDigit == 7 || firstDigit == 9)
			modulo = 11;

		//C&#65533;lculo do segundo d&#65533;gito
		int d2 = -1; //segundo d&#65533;gito verificador
		int soma = 0;
		int peso = ie.length()==8 ? 7 : 8;
		for(int i = 0; i < ie.length() - 2; i++){
			soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
			peso--;
		}

		int resto = soma % modulo;

		if (resto == 0 || (modulo == 11 && resto == 1)){
			d2 = 0;
		} else {
			d2 = modulo - resto;
		}

		//C&#65533;lculo do primeiro d&#65533;gito
		int d1 = -1; //primeiro d&#65533;gito verificador
		soma = d2 * 2;
		peso = ie.length()==8 ? 8 : 9;
		for(int i = 0; i < ie.length() - 2; i++){
			soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
			peso--;
		}

		resto = soma % modulo; 

		if (resto == 0 || (modulo == 11 && resto == 1)){
			d1 = 0;
		} else {
			d1 = modulo - resto;
		}

		//valida os digitos verificadores
		String dv = d1 + "" + d2;
		if (!dv.equals(ie.substring(ie.length() - 2, ie.length()))){
			throw new Exception("Digito verificador inv�lido." + ie);
		}
	}

	/**
	 * Valida inscri&#65533;&#65533;o estadual do estado do Cear&#65533;
	 * @param ie (Inscri&#65533;&#65533;o estadual)
	 * @throws Exception 
	 */
	private static void validaIECeara(String ie) throws Exception{
		//valida quantida de d&#65533;gitos
		if (ie.length() != 9){
			throw new Exception("Quantidade de digitos inv�lidas.");
		}

		//C&#65533;lculo do d&#65533;gito verificador
		int soma = 0;
		int peso = 9;
		int d = -1; //d&#65533;gito verificador
		for(int i = 0; i < ie.length() - 1; i++){
			soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
			peso--;
		}

		d = 11 - (soma % 11);
		if(d == 10 || d == 11){
			d = 0;			
		}
		//valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
			throw new Exception("Digito verificador inv�lido.");
		}
	}

	/**
	 * Valida inscri&#65533;&#65533;o estadual do estado do Esp&#65533;rito Santo
	 * @param ie (Inscri&#65533;&#65533;o estadual)
	 * @throws Exception 
	 */
	private static void validaIEEspiritoSanto(String ie) throws Exception{
		//valida quantida de d&#65533;gitos
		if (ie.length() != 9){
			throw new Exception("Quantidade de digitos inv�lidas.");
		}

		//C&#65533;lculo do d&#65533;gito verificador
		int soma = 0;
		int peso = 9;
		int d = -1; //d&#65533;gito verificador
		for(int i = 0; i < ie.length() - 1; i++){
			soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
			peso--;
		}

		int resto = soma % 11;
		if (resto < 2){
			d = 0;
		} else if (resto > 1){
			d = 11 - resto;
		}

		//valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
			throw new Exception("Digito verificador inv�lido.");
		}
	}

	/**
	 * Valida inscri&#65533;&#65533;o estadual do estado do Goi&#65533;s
	 * @param ie (Inscri&#65533;&#65533;o estadual)
	 * @throws Exception 
	 */
	private static void validaIEGoias(String ie) throws Exception{
		//valida quantida de d&#65533;gitos
		if (ie.length() != 9){
			throw new Exception("Quantidade de digitos inv�lidas.");
		}

		//v&#65533;lida os dois primeiros d&#65533;gito
		if (!"10".equals(ie.substring(0, 2))){
			if (!"11".equals(ie.substring(0, 2))){
				if (!"15".equals(ie.substring(0, 2))){
					throw new Exception("Inscri��o estadual inv�lida");
				}
			}
		}

		if (ie.substring(0, ie.length() - 1).equals("11094402")){
			if (!ie.substring(ie.length() - 1, ie.length()).equals("0")){
				if (!ie.substring(ie.length() - 1, ie.length()).equals("1")){
					throw new Exception("Inscri��o estadual inv�lida.");
				}
			}
		} else {

			//C&#65533;lculo do d&#65533;gito verificador
			int soma = 0;
			int peso = 9;
			int d = -1; //d&#65533;gito verificador
			for(int i = 0; i < ie.length() - 1; i++){
				soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
				peso--;
			}

			int resto = soma % 11;
			long faixaInicio = 10103105;
			long faixaFim = 10119997;
			long insc = Long.parseLong(ie.substring(0, ie.length() -1));
			if (resto == 0){
				d = 0;
			} else if (resto == 1){
				if (insc >= faixaInicio && insc <= faixaFim){
					d = 1;
				} else {
					d = 0;
				}
			} else if (resto != 0 && resto != 1){
				d = 11 - resto;
			} 

			//valida o digito verificador
			String dv = d + "";
			if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
				throw new Exception("Digito verificador inv�lido.");
			}
		}
	}

	/**
	 * Valida inscri&#65533;&#65533;o estadual do estado do Maranh&#65533;o
	 * @param ie (Inscri&#65533;&#65533;o estadual)
	 * @throws Exception 
	 */
	private static void validaIEMaranhao(String ie) throws Exception{
		//valida quantida de d&#65533;gitos
		if (ie.length() != 9){
			throw new Exception("Quantidade de digitos inv�lidas.");
		}

		//valida os dois primeiros d&#65533;gitos
		if(!ie.substring(0, 2).equals("12")){
			throw new Exception("Inscri��o estadual inv�lida.");
		}

		//Calcula o d&#65533;gito verificador
		int soma = 0;
		int peso = 9;
		int d = -1; //d&#65533;gito verificador
		for(int i = 0; i < ie.length() - 1; i++){
			soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
			peso--;
		}

		d = 11 - (soma % 11);
		if ((soma % 11) == 0 || (soma % 11) == 1){
			d = 0;
		} 

		//valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
			throw new Exception("Digito verificador inv�lido.");
		}
	}

	/**
	 * Valida inscri&#65533;&#65533;o estadual do estado do Mato Grosso
	 * @param ie (Inscri&#65533;&#65533;o estadual)
	 * @throws Exception 
	 */
	private static void validaIEMatoGrosso(String ie) throws Exception{
		//valida quantida de d&#65533;gitos
		if (ie.length() != 11){
			throw new Exception("Quantidade de digitos inv�lidas.");
		}

		//Calcula o d&#65533;gito verificador
		int soma = 0;
		int pesoInicial = 3;
		int pesoFinal = 9;
		int d = -1;

		for(int i = 0; i < ie.length() - 1; i++){
			if (i < 2){
				soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicial;
				pesoInicial--;
			} 
			else {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFinal;
				pesoFinal--;
			}
		}

		d = 11 - (soma % 11);
		if ((soma % 11) == 0 ||(soma % 11) == 1){
			d = 0;
		}

		//valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
			throw new Exception("Digito verificador inv�lido.");
		}
	}

	/**
	 * Valida inscri&#65533;&#65533;o estadual do estado do Mato Grosso do Sul
	 * @param ie (Inscri&#65533;&#65533;o estadual)
	 * @throws Exception 
	 */
	private static void validaIEMatoGrossoSul(String ie) throws Exception{
		//valida quantida de d&#65533;gitos
		if (ie.length() != 9){
			throw new Exception("Quantidade de digitos inv�lidas.");
		}

		//valida os dois primeiros d&#65533;gitos
		if (!ie.substring(0, 2).equals("28")){
			throw new Exception("Inscri��o estadual inv�lida.");
		}

		//Calcula o d&#65533;gito verificador
		int soma = 0;
		int peso = 9;
		int d = -1; //d&#65533;gito verificador
		for(int i = 0; i < ie.length() - 1; i++){
			soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
			peso--;
		}

		int resto = soma % 11;
		int result = 11 - resto;
		if (resto == 0){
			d = 0;
		} 
		else if (resto > 0){
			if(result > 9){
				d = 0;
			} else if (result < 10){
				d = result;
			}
		}

		//valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
			throw new Exception("Digito verificador inv�lido.");
		}
	}

	/**
	 * Valida inscri&#65533;&#65533;o estadual do estado do Minas Gerais
	 * @param ie (Inscri&#65533;&#65533;o estadual)
	 * @throws Exception
	 */
	private static void validaIEMinasGerais(String ie) throws Exception {
		/*
		 * FORMATO GERAL: A1A2A3B1B2B3B4B5B6C1C2D1D2
		 * Onde: A= C&#65533;digo do Munic&#65533;pio
		 * B= N&#65533;mero da inscri&#65533;&#65533;o
		 * C= N&#65533;mero de ordem do estabelecimento
		 * D= D&#65533;gitos de controle
		 */

		// valida quantida de d&#65533;gitos
		if (ie.length() != 13) {
			throw new Exception("Quantidade de digitos inv�lidas.");
		}

		//iguala a casas para o c&#65533;lculo
		//em inserir o algarismo zero "0" imediatamente ap&#65533;s o n&#65533;mero de c&#65533;digo do munic&#65533;pio, 
		//desprezando-se os d&#65533;gitos de controle.
		String str = "";
		for(int i = 0; i < ie.length() - 2; i++){
			if (Character.isDigit(ie.charAt(i))){
				if (i == 3){
					str += "0";
					str += ie.charAt(i);
				} else {
					str += ie.charAt(i);
				}
			}
		}

		//C&#65533;lculo do primeiro d&#65533;gito verificador
		int soma = 0;
		int pesoInicio = 1;
		int pesoFim = 2;
		int d1 = -1; //primeiro d&#65533;gito verificador
		for(int i = 0; i < str.length(); i++ ){
			if (i % 2 == 0){
				int x = Integer.parseInt(String.valueOf(str.charAt(i))) * pesoInicio;
				String strX = Integer.toString(x);
				for(int j = 0; j < strX.length(); j++){
					soma += Integer.parseInt(String.valueOf(strX.charAt(j)));
				}
			} else {
				int y = Integer.parseInt(String.valueOf(str.charAt(i))) * pesoFim;
				String strY = Integer.toString(y);
				for(int j = 0; j < strY.length(); j++){
					soma += Integer.parseInt(String.valueOf(strY.charAt(j)));
				}
			}
		}

		int dezenaExata = soma;
		while(dezenaExata % 10 != 0){
			dezenaExata++;
		}
		d1 = dezenaExata - soma; //resultado - primeiro d&#65533;gito verificador

		//C&#65533;lculo do segundo d&#65533;gito verificador
		soma = d1 * 2;
		pesoInicio = 3;
		pesoFim = 11;
		int d2 = -1;
		for(int i = 0; i < ie.length() - 2; i++){
			if(i < 2){
				soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
				pesoInicio--;
			} else {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
				pesoFim--;
			}
		}

		d2 = 11 - (soma % 11); //resultado - segundo d&#65533;gito verificador
		if ((soma % 11 == 0) || (soma % 11 == 1)){
			d2 = 0;
		}

		//valida os digitos verificadores
		String dv = d1 + "" + d2;
		if (!dv.equals(ie.substring(ie.length() - 2, ie.length()))){
			throw new Exception("Digito verificador inv�lido.");
		}
	}

	/**
	 * Valida inscri&#65533;&#65533;o estadual do estado do Par&#65533;
	 * @param ie (Inscri&#65533;&#65533;o estadual)
	 * @throws Exception 
	 */
	private static void validaIEPara(String ie) throws Exception{
		//valida quantida de d&#65533;gitos
		if (ie.length() != 9){
			throw new Exception("Quantidade de digitos inv�lidas.");
		}

		//valida os dois primeiros d&#65533;gitos
		if (!ie.substring(0, 2).equals("15")){
			throw new Exception("Inscri��o estadual inv�lida.");
		}

		//Calcula o d&#65533;gito verificador
		int soma = 0;
		int peso = 9;
		int d = -1; //d&#65533;gito verificador
		for(int i = 0; i < ie.length() - 1; i++){
			soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
			peso--;
		}

		d = 11 - (soma % 11);
		if((soma % 11) == 0 || (soma % 11) == 1){
			d = 0;
		}

		//valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
			throw new Exception("Digito verificador inv�lido.");
		}
	}

	/**
	 * Valida inscri&#65533;&#65533;o estadual do estado do Para&#65533;ba
	 * @param ie (Inscri&#65533;&#65533;o estadual)
	 * @throws Exception 
	 */
	private static void validaIEParaiba(String ie) throws Exception{
		//valida quantida de d&#65533;gitos
		if (ie.length() != 9){
			throw new Exception("Quantidade de digitos inv�lidas.");
		}

		//Calcula o d&#65533;gito verificador
		int soma = 0;
		int peso = 9;
		int d = -1; //d&#65533;gito verificador
		for(int i = 0; i < ie.length() - 1; i++){
			soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
			peso--;
		}

		d = 11 - (soma % 11);
		if (d == 10 || d == 11){
			d = 0;
		}

		//valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
			throw new Exception("Digito verificador inv�lido.");
		}
	}

	/**
	 * Valida inscri&#65533;&#65533;o estadual do estado do Paran&#65533;
	 * @param ie (Inscri&#65533;&#65533;o estadual)
	 * @throws Exception 
	 */
	private static void validaIEParana(String ie) throws Exception{
		//valida quantida de d&#65533;gitos
		if (ie.length() != 10){
			throw new Exception("Quantidade de digitos inv�lidas.");
		}

		//C&#65533;lculo do primeiro d&#65533;gito
		int soma = 0;
		int pesoInicio = 3;
		int pesoFim = 7;
		int d1 = -1; //d&#65533;gito verificador
		for(int i = 0; i < ie.length() - 2; i++){
			if(i < 2){
				soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
				pesoInicio--;
			} else {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
				pesoFim--;
			}
		}

		d1 = 11 - (soma % 11);
		if ((soma % 11) == 0 || (soma % 11) == 1){
			d1 = 0;
		}

		//c&#65533;lculo do segundo d&#65533;gito
		soma = d1 * 2;
		pesoInicio = 4;
		pesoFim = 7;
		int d2 = -1; //segundo d&#65533;gito
		for(int i = 0; i < ie.length() - 2; i++){
			if (i < 3){
				soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
				pesoInicio--;
			} else {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
				pesoFim--;
			}
		}

		d2 = 11 - (soma % 11);
		if ((soma % 11) == 0 || (soma % 11) == 1){
			d2 = 0;
		}

		//valida os digitos verificadores
		String dv = d1 + "" + d2;
		if (!dv.equals(ie.substring(ie.length() - 2, ie.length()))){
			throw new Exception("Digito verificador inv�lido.");
		}
	}

	/**
	 * Valida inscri&#65533;&#65533;o estadual do estado do Pernambuco
	 * @param ie (Inscri&#65533;&#65533;o estadual)
	 * @throws Exception 
	 */
	private static void validaIEPernambuco(String ie) throws Exception{
		//valida quantida de d&#65533;gitos
		if (ie.length() != 14){
			throw new Exception("Quantidade de digitos inv�lidas.");
		}

		//C&#65533;lculo do d&#65533;gito verificador
		int soma = 0;
		int pesoInicio = 5;
		int pesoFim = 9;
		int d = -1; //d&#65533;gito verificador

		for(int i = 0; i < ie.length() - 1; i++){
			if(i < 5){
				soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
				pesoInicio--;
			} else {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
				pesoFim--;
			}
		}

		d = 11 - (soma % 11);
		if (d > 9){
			d -= 10;
		}

		System.out.println(soma);
		System.out.println(11 - (soma % 11));
		System.out.println(d);

		//valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
			throw new Exception("Digito verificador inv�lido.");
		}
	}

	/**
	 * Valida inscri&#65533;&#65533;o estadual do estado do Piau&#65533;
	 * @param ie (Inscri&#65533;&#65533;o estadual)
	 * @throws Exception 
	 */
	private static void validaIEPiaui(String ie) throws Exception{
		//valida quantida de d&#65533;gitos
		if (ie.length() != 9){
			throw new Exception("Quantidade de digitos inv�lidas.");
		}

		//C&#65533;lculo do d&#65533;gito verficador
		int soma = 0;
		int peso = 9;
		int d = -1; //d&#65533;gito verificador
		for(int i = 0; i < ie.length() - 1; i++){
			soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
			peso--;
		}

		d = 11 - (soma % 11);
		if (d == 11 || d == 10){
			d = 0;
		}

		//valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
			throw new Exception("Digito verificador inv�lido.");
		}
	}

	/**
	 * Valida inscri&#65533;&#65533;o estadual do estado do Rio de Janeiro
	 * @param ie (Inscri&#65533;&#65533;o estadual)
	 * @throws Exception 
	 */
	private static void validaIERioJaneiro(String ie) throws Exception{
		//valida quantida de d&#65533;gitos
		if (ie.length() != 8){
			throw new Exception("Quantidade de digitos inv�lidas.");
		}

		//C&#65533;lculo do d&#65533;gito verficador
		int soma = 0;
		int peso = 7;
		int d = -1; //d&#65533;gito verificador
		for(int i = 0; i < ie.length() - 1; i++){
			if(i == 0){
				soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * 2;
			} else {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
				peso--;
			}
		}

		d = 11 - (soma % 11);
		if ((soma % 11) <= 1){
			d = 0;
		}

		//valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
			throw new Exception("Digito verificador inv�lido.");
		}
	}

	/**
	 * Valida inscri&#65533;&#65533;o estadual do estado do Rio Grande do Norte
	 * @param ie (Inscri&#65533;&#65533;o estadual)
	 * @throws Exception 
	 */
	private static void validaIERioGrandeNorte(String ie) throws Exception{
		//valida quantida de d&#65533;gitos
		if (ie.length() != 10 && ie.length() != 9){
			throw new Exception("Quantidade de digitos inv�lidas.");
		}

		//valida os dois primeiros d&#65533;gitos
		if (!ie.substring(0, 2).equals("20")){
			throw new Exception("Inscri��o estadual inv�lida.");
		}

		//calcula o d&#65533;gito para inscri&#65533;&#65533;o de 9 d&#65533;gitos
		if (ie.length() == 9){
			int soma = 0;
			int peso = 9;
			int d = -1; //d&#65533;gito verificador
			for(int i = 0; i < ie.length() - 1; i++){
				soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
				peso--;
			}

			d = ((soma * 10) % 11);
			if (d == 10){
				d = 0;
			}

			//valida o digito verificador
			String dv = d + "";
			if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
				throw new Exception("Digito verificador inv�lido.");
			}
		} else {
			int soma = 0;
			int peso = 10;
			int d = -1; //d&#65533;gito verificador
			for(int i = 0; i < ie.length() - 1; i++){
				soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
				peso--;
			}
			d = ((soma * 10) % 11);
			if (d == 10){
				d = 0;
			}

			//valida o digito verificador
			String dv = d + "";
			if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
				throw new Exception("Digito verificador inv�lido.");
			}
		}

	}

	/**
	 * Valida inscri&#65533;&#65533;o estadual do estado do Rio Grande do Sul
	 * @param ie (Inscri&#65533;&#65533;o estadual)
	 * @throws Exception 
	 */
	private static void validaIERioGrandeSul(String ie) throws Exception{
		//valida quantida de d&#65533;gitos
		if (ie.length() != 10){
			throw new Exception("Quantidade de digitos inv�lidas.");
		}

		//C&#65533;lculo do d&#65533;fito verificador
		int soma = Integer.parseInt(String.valueOf(ie.charAt(0))) * 2;
		int peso = 9;
		int d = -1; //d&#65533;gito verificador
		for(int i = 1; i < ie.length() - 1; i++){
			soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
			peso--;
		}

		d = 11 - (soma % 11);
		if (d == 10 || d == 11){
			d = 0;
		}

		//valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
			throw new Exception("Digito verificador inv�lido.");
		}
	}

	/**
	 * Valida inscri&#65533;&#65533;o estadual do estado do Rond&#65533;nia
	 * @param ie (Inscri&#65533;&#65533;o estadual)
	 * @throws Exception 
	 */
	private static void validaIERondonia(String ie) throws Exception{
		//valida quantida de d&#65533;gitos
		if (ie.length() != 14){
			throw new Exception("Quantidade de digitos inv�lidas.");
		}

		//C&#65533;lculo do d&#65533;gito verificador
		int soma = 0;
		int pesoInicio = 6;
		int pesoFim = 9;
		int d = -1; //d&#65533;gito verificador
		for(int i = 0; i < ie.length() - 1; i++){
			if( i < 5){
				soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
				pesoInicio--;
			}
			else {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
				pesoFim--;
			}
		}

		d = 11 - (soma % 11);
		if(d == 11 || d == 10){
			d -= 10;
		}

		//valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
			throw new Exception("Digito verificador inv�lido.");
		}
	}

	/**
	 * Valida inscri&#65533;&#65533;o estadual do estado do Rora&#65533;ma
	 * @param ie (Inscri&#65533;&#65533;o estadual)
	 * @throws Exception 
	 */
	private static void validaIERoraima(String ie) throws Exception{
		//valida quantida de d&#65533;gitos
		if (ie.length() != 9){
			throw new Exception("Quantidade de digitos inv�lidas.");
		}

		//valida os dois primeiros d&#65533;gitos
		if (!ie.substring(0, 2).equals("24")){
			throw new Exception("Inscri��o estadual inv�lida.");
		}

		int soma = 0;
		int peso = 1;
		int d = -1; //d&#65533;gito verificador
		for(int i = 0; i < ie.length() - 1; i++){
			soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
			peso++;
		}

		d = soma % 9;

		//valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
			throw new Exception("Digito verificador inv�lido.");
		}
	}

	/**
	 * Valida inscri&#65533;&#65533;o estadual do estado do Santa Catarina
	 * @param ie (Inscri&#65533;&#65533;o estadual)
	 * @throws Exception 
	 */
	private static void validaIESantaCatarina(String ie) throws Exception{
		//valida quantida de d&#65533;gitos
		if (ie.length() != 9){
			throw new Exception("Quantidade de digitos inv�lidas.");
		}

		//C&#65533;lculo do d&#65533;fito verificador
		int soma = 0;
		int peso = 9;
		int d = -1; //d&#65533;gito verificador
		for(int i = 0; i < ie.length() - 1; i++){
			soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
			peso--;
		}

		d = 11 - (soma % 11);
		if ((soma % 11) == 0 || (soma % 11) == 1){
			d = 0;
		}

		//valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
			throw new Exception("Digito verificador inv�lido.");
		}
	}

	/**
	 * Valida inscri&#65533;&#65533;o estadual do estado do S&#65533;o Paulo
	 * @param ie (Inscri&#65533;&#65533;o estadual)
	 * @throws Exception 
	 */
	private static void validaIESaoPaulo(String ie) throws Exception{
		//valida quantida de d&#65533;gitos
		if (ie.length() != 12 && ie.length() != 13){
			throw new Exception("Quantidade de digitos inv�lidas.");
		}

		if(ie.length() == 12){
			int soma = 0;
			int peso = 1;
			int d1 = -1; //primeiro d&#65533;gito verificador
			//c&#65533;lculo do primeiro d&#65533;gito verificador (nona posi&#65533;&#65533;o)
			for(int i = 0; i < ie.length() - 4; i++){
				if(i == 1 || i == 7){
					soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * ++peso;
					peso++;
				} else {
					soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
					peso++;
				}
			}

			d1 = soma % 11;
			String strD1 = Integer.toString(d1); //O d&#65533;gito &#65533; igual ao algarismo mais a direita do resultado de (soma % 11)
			d1 = Integer.parseInt(String.valueOf(strD1.charAt(strD1.length() - 1)));

			//c&#65533;lculo do segunfo d&#65533;gito
			soma = 0;
			int pesoInicio = 3;
			int pesoFim = 10;
			int d2 = -1; //segundo d&#65533;gito verificador
			for(int i = 0; i < ie.length() - 1; i++){
				if(i < 2){
					soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
					pesoInicio--;
				} else {
					soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
					pesoFim--;
				}
			}

			d2 = soma % 11;
			String strD2 = Integer.toString(d2); //O d&#65533;gito &#65533; igual ao algarismo mais a direita do resultado de (soma % 11)
			d2 = Integer.parseInt(String.valueOf(strD2.charAt(strD2.length() - 1)));

			//valida os d&#65533;gitos verificadores
			if(!ie.substring(8, 9).equals(d1 + "")){
				throw new Exception("Inscri��o estadual inv�lida.");
			}
			if(!ie.substring(11, 12).equals(d2 + "")){
				throw new Exception("Inscri��o estadual inv�lida.");
			}

		} else {
			//valida o primeiro caracter
			if(ie.charAt(0) != 'P'){
				throw new Exception("Inscri��o estadual inv�lida.");
			}

			String strIE = ie.substring(1, 10); //Obt&#65533;m somente os d&#65533;gitos utilizados no c&#65533;lculo do d&#65533;gito verificador
			int soma = 0;
			int peso = 1;
			int d1 = -1; //primeiro d&#65533;gito verificador
			//c&#65533;lculo do primeiro d&#65533;gito verificador (nona posi&#65533;&#65533;o)
			for(int i = 0; i < strIE.length() - 1; i++){
				if(i == 1 || i == 7){
					soma += Integer.parseInt(String.valueOf(strIE.charAt(i))) * ++peso;
					peso++;
				} else {
					soma += Integer.parseInt(String.valueOf(strIE.charAt(i))) * peso;
					peso++;
				}
			}

			d1 = soma % 11;
			String strD1 = Integer.toString(d1); //O d&#65533;gito &#65533; igual ao algarismo mais a direita do resultado de (soma % 11)
			d1 = Integer.parseInt(String.valueOf(strD1.charAt(strD1.length() - 1)));

			//valida o d&#65533;gito verificador
			if(!ie.substring(9, 10).equals(d1 + "")){
				throw new Exception("Inscri��o estadual inv�lida.");
			}
		}
	}

	/**
	 * Valida inscri&#65533;&#65533;o estadual do estado do Sergipe
	 * @param ie (Inscri&#65533;&#65533;o estadual)
	 * @throws Exception 
	 */
	private static void validaIESergipe(String ie) throws Exception{
		//valida quantida de d&#65533;gitos
		if (ie.length() != 9){
			throw new Exception("Quantidade de digitos inv�lidas.");
		}

		//c&#65533;lculo do d&#65533;gito verificador
		int soma = 0;
		int peso = 9;
		int d = -1; //d&#65533;gito verificador
		for(int i = 0; i < ie.length() - 1; i++){
			soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
			peso--;
		}

		d = 11 - (soma % 11);
		if(d == 11 || d == 11 || d == 10){
			d = 0;
		}

		//valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
			throw new Exception("Digito verificador inv�lido.");
		}
	}

	/**
	 * Valida inscri&#65533;&#65533;o estadual do estado do Tocantins
	 * @param ie (Inscri&#65533;&#65533;o estadual)
	 * @throws Exception 
	 */
	private static void validaIETocantins(String ie) throws Exception {
		//valida quantida de d&#65533;gitos
		if (ie.length() != 9 && ie.length() != 11){
			throw new Exception("Quantidade de d&#65533;gitos inv&#65533;lida.");
		} else if (ie.length() == 9) {
			ie = ie.substring(0, 2) + "02" + ie.substring(2);
		}

		int soma = 0;
		int peso = 9;
		int d = -1; //d&#65533;gito verificador
		for(int i = 0; i < ie.length() - 1; i++){
			if(i != 2 && i != 3){
				soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
				peso--;
			}
		}
		d = 11 - (soma % 11);
		if ((soma % 11) < 2){
			d = 0;
		}

		//valida o digito verificador
		String dv = d + "";
		if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)){
			throw new Exception("Digito verificador inv�lido.");
		}
	}

	/**
	 * Valida inscri&#65533;&#65533;o estadual do estado do Distrito Federal
	 * @param ie (Inscri&#65533;&#65533;o estadual)
	 * @throws Exception 
	 */
	private static void validaIEDistritoFederal(String ie) throws Exception {
		//valida quantida de d&#65533;gitos
		if (ie.length() != 13){
			throw new Exception("Quantidade de digitos inv�lidas.");
		}

		//c&#65533;lculo do primeiro d&#65533;gito verificador
		int soma = 0;
		int pesoInicio = 4;
		int pesoFim = 9;
		int d1 = -1; //primeiro d&#65533;gito verificador
		for(int i = 0; i < ie.length() - 2; i++){
			if(i < 3){
				soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
				pesoInicio--;
			} else {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
				pesoFim--;
			}
		}

		d1 = 11 - (soma % 11);
		if(d1 == 11 || d1 == 10){
			d1 = 0;
		}

		//c&#65533;lculo do segundo d&#65533;gito verificador
		soma = d1 * 2;
		pesoInicio = 5;
		pesoFim = 9;
		int d2 = -1; //segundo d&#65533;gito verificador
		for(int i = 0; i < ie.length() - 2; i++){
			if (i < 4){
				soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
				pesoInicio--;
			} else {
				soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
				pesoFim--;
			}
		}

		d2 = 11 - (soma % 11);
		if(d2 == 11 || d2 == 10){
			d2 = 0;
		}

		//valida os digitos verificadores
		String dv = d1 + "" + d2;
		if (!dv.equals(ie.substring(ie.length() - 2, ie.length()))){
			throw new Exception("Digito verificador inv�lido.");
		}
	}

}
