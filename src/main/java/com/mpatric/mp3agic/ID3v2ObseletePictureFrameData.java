package com.mpatric.mp3agic;

import java.io.UnsupportedEncodingException;

public class ID3v2ObseletePictureFrameData extends ID3v2PictureFrameData {

	public ID3v2ObseletePictureFrameData(boolean unsynchronisation) {
		super(unsynchronisation);
	}
	
	public ID3v2ObseletePictureFrameData(boolean unsynchronisation, String mimeType, byte pictureType, EncodedText description, byte[] imageData) {
		super(unsynchronisation, mimeType, pictureType, description, imageData);
	}

	public ID3v2ObseletePictureFrameData(boolean unsynchronisation, byte[] bytes) throws InvalidDataException {
		super(unsynchronisation, bytes);
	}

	@Override
	protected void unpackFrameData(byte[] bytes) throws InvalidDataException {
		String filetype;
		try {
			filetype = BufferTools.byteBufferToString(bytes, 1, 3);
		} catch (UnsupportedEncodingException e) {
			filetype = "unknown";
		} 
		mimeType = "image/" + filetype.toLowerCase();
		pictureType = bytes[4];
		int marker;
		for (marker = 5; marker < bytes.length; marker++) {
			if (bytes[marker] == 0) break;
		}
		description = new EncodedText(bytes[0], BufferTools.copyBuffer(bytes, 5, marker - 5));
		imageData = BufferTools.copyBuffer(bytes, marker + 1, bytes.length - marker - 1);
	}
}
