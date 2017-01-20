package com.pms;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class WebService {
	
	private static String NAMESPACE = "http://tempuri.org/";
	private static String URL = "http://103.14.96.140/wipro/PMSWebService.asmx";
	private static String SOAP_ACTION = "http://tempuri.org/";
	
	public static String CreateLogin(String username, String pasword, String webMethName) {
		String resTxt = null;
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, webMethName);
		// Property which holds input parameters
		PropertyInfo celsiusPI = new PropertyInfo();
		// Set Name
		celsiusPI.setName("username");
		// Set Value
		celsiusPI.setValue(username);
		// Set dataType
		celsiusPI.setType(String.class);
		// Add the property to request object
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("password");
		celsiusPI.setValue(pasword);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			// Invole web service
			androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
			// Get the response
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// Assign it to fahren static variable
			resTxt = response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			resTxt = "No Network Found";
		} 
		
		return resTxt;
	}

	public static String AddSelfie(int merchandiserId, String photo, String webMethName) {
		String resTxt = null;
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, webMethName);

		PropertyInfo celsiusPI = new PropertyInfo();
		celsiusPI.setName("merchandiserId");
		celsiusPI.setValue(merchandiserId);
		celsiusPI.setType(Integer.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("bmp");
		celsiusPI.setValue(photo);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);


		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			// Invole web service
			androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
			// Get the response
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// Assign it to fahren static variable
			resTxt = response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			resTxt = "Error occured";
		}
		return resTxt;
	}

	public static String allStoreList(int merchandiserId,String storeListMethName) {
			String resTxt = null;
			// Create request
			SoapObject request = new SoapObject(NAMESPACE, storeListMethName);

			PropertyInfo celsiusPI = new PropertyInfo();
			celsiusPI.setName("MerchandiserID");
			celsiusPI.setValue(merchandiserId);
			celsiusPI.setType(Integer.class);
			request.addProperty(celsiusPI);

			// Create envelope
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = true;
			// Set output SOAP object
			envelope.setOutputSoapObject(request);
			// Create HTTP call object
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

			try {
				// Invole web service
				androidHttpTransport.call(SOAP_ACTION + storeListMethName, envelope);
				// Get the response
				SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
				// Assign it to fahren static variable
				resTxt = response.toString();

			} catch (Exception e) {
				e.printStackTrace();
				resTxt = "No Network Found";
			}
			return resTxt;
	}

	public static String doneStoreList(int merchandiserId,String webMethName) {
		String resTxt = null;

		PropertyInfo celsiusPI;
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, webMethName);

		celsiusPI = new PropertyInfo();
		celsiusPI.setName("MerchandiserID");
		celsiusPI.setValue(merchandiserId);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			// Invole web service
			androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
			// Get the response
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// Assign it to fahren static variable
			resTxt = response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			resTxt = "No Network Found";
		}
		return resTxt;
	}



	public static String SaveImages(int questionId, String images, String storeImageUploadMethod, int count) {
		String resTxt = null;
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, storeImageUploadMethod);

		PropertyInfo celsiusPI = new PropertyInfo();
		celsiusPI.setName("qid");
		celsiusPI.setValue(questionId);
		celsiusPI.setType(Integer.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("imageByte");
		celsiusPI.setValue(images);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("count");
		celsiusPI.setValue(count);
		celsiusPI.setType(Integer.class);
		request.addProperty(celsiusPI);

	/*	celsiusPI=new PropertyInfo();
		celsiusPI.setName("image2");
		celsiusPI.setValue(image2);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("image3");
		celsiusPI.setValue(image3);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("image4");
		celsiusPI.setValue(image4);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("image5");
		celsiusPI.setValue(image5);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);*/

		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			// Invole web service
			androidHttpTransport.call(SOAP_ACTION + storeImageUploadMethod, envelope);
			// Get the response
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// Assign it to fahren static variable
			resTxt = response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			resTxt = "Error occured";
		}
		return resTxt;
	}


	public static String AddNewQuestion(String addQuestioneMethodName, int userId, boolean windowMerchandising, int noofDanglers,
										int noofPosters, String banners, String anyOther, boolean windowShelfSpaces,
										boolean fourfacingsIncategoryshelf, String str125gx4, String stw, String sbs,
										String gld, String wcfBottle, String sbl, String issueToHighlight,
										String exitWithoutExecnapprvBy, String reasonNonCooperationofoutlet,
										boolean workedwithStarSalesman, String images, String longitude,
										String latitude, int retailerId, int beatId, boolean shopOpen) {

		String resTxt = null;
		PropertyInfo celsiusPI;
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, addQuestioneMethodName);

		celsiusPI = new PropertyInfo();
		celsiusPI.setName("UserId");
		celsiusPI.setValue(userId);
		celsiusPI.setType(Integer.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("WindowMerchandising");
		celsiusPI.setValue(windowMerchandising);
		celsiusPI.setType(boolean.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("NoofDanglers");
		celsiusPI.setValue(noofDanglers);
		celsiusPI.setType(Integer.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("NoofPosters");
		celsiusPI.setValue(noofPosters);
		celsiusPI.setType(Integer.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("Banners");
		celsiusPI.setValue(banners);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("AnyOther");
		celsiusPI.setValue(anyOther);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("WindowShelfSpaces");
		celsiusPI.setValue(windowShelfSpaces);
		celsiusPI.setType(boolean.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("FourfacingsIncategoryshelf");
		celsiusPI.setValue(fourfacingsIncategoryshelf);
		celsiusPI.setType(boolean.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("STR125gx4");
		celsiusPI.setValue(str125gx4);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("STW");
		celsiusPI.setValue(stw);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("SBS");
		celsiusPI.setValue(sbs);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("GLD");
		celsiusPI.setValue(gld);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("WCFBottle");
		celsiusPI.setValue(wcfBottle);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("SBL");
		celsiusPI.setValue(sbl);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("IssueToHighlight");
		celsiusPI.setValue(issueToHighlight);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("ExitWithoutExecnapprvBy");
		celsiusPI.setValue(exitWithoutExecnapprvBy);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("ReasonNonCooperationofoutlet");
		celsiusPI.setValue(reasonNonCooperationofoutlet);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("WorkedwithStarSalesman");
		celsiusPI.setValue(workedwithStarSalesman);
		celsiusPI.setType(boolean.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("Images");
		celsiusPI.setValue(images);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("Longitude");
		celsiusPI.setValue(longitude);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("Latitude");
		celsiusPI.setValue(latitude);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("RetailerId");
		celsiusPI.setValue(retailerId);
		celsiusPI.setType(Integer.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("BeatId");
		celsiusPI.setValue(beatId);
		celsiusPI.setType(Integer.class);
		request.addProperty(celsiusPI);

		celsiusPI=new PropertyInfo();
		celsiusPI.setName("IsOpen");
		celsiusPI.setValue(shopOpen);
		celsiusPI.setType(boolean.class);
		request.addProperty(celsiusPI);

			// Create envelope
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = true;
			// Set output SOAP object
			envelope.setOutputSoapObject(request);
			// Create HTTP call object
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			try {
				// Invole web service
				androidHttpTransport.call(SOAP_ACTION + addQuestioneMethodName, envelope);
				// Get the response
				SoapPrimitive  response = (SoapPrimitive) envelope.getResponse();
				// Assign it to fahren static variable
				resTxt = response.toString();

			} catch (Exception e) {
				e.printStackTrace();
				resTxt = "Error occured";
			}
		return resTxt;
	}

	public static String checkSelfie(int merchantId, String checkselfieMethName) {
		String resTxt = null;

		PropertyInfo celsiusPI;
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, checkselfieMethName);

		celsiusPI = new PropertyInfo();
		celsiusPI.setName("MerchandiserID");
		celsiusPI.setValue(merchantId);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			// Invole web service
			androidHttpTransport.call(SOAP_ACTION+checkselfieMethName, envelope);
			// Get the response
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// Assign it to fahren static variable
			resTxt = response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			resTxt = "No Network Found";
		}
		return resTxt;
	}

	public static String FetchPerformanceDetails(int merchandiserId,String webMethName) {
		String resTxt = null;

		PropertyInfo celsiusPI;
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, webMethName);

		celsiusPI = new PropertyInfo();
		celsiusPI.setName("merchandiserId");
		celsiusPI.setValue(merchandiserId);
		celsiusPI.setType(String.class);
		request.addProperty(celsiusPI);

		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			// Invole web service
			androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
			// Get the response
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// Assign it to fahren static variable
			resTxt = response.toString();

		} catch (Exception e) {
			e.printStackTrace();
			resTxt = "No Network Found";
		}
		return resTxt;
	}



}
