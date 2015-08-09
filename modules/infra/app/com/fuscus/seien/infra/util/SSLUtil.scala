package com.fuscus.seien.infra.util

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets
import java.security.cert.{ CertificateFactory, X509Certificate }

trait SSLUtil {
  /**
   * get expired from certificate
   *
   * @param certificateString unescaped string
   * @return
   */
  def expiredValidity(certificateString: String): Long = {
    val cert: X509Certificate = generateCertificate(certificateString)
    cert.getNotAfter.getTime
  }

  /**
   * generate certificate(X509)
   *
   * @param certificateString unescaped string
   * @return
   */
  private def generateCertificate(certificateString: String): X509Certificate = {
    CertificateFactory.getInstance("X.509").generateCertificate(
      new ByteArrayInputStream(certificateString.getBytes(StandardCharsets.UTF_8))).asInstanceOf[X509Certificate]
  }
}
