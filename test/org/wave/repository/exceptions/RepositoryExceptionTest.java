package org.wave.repository.exceptions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.wave.repository.enums.ErrorEnum;
import org.wave.repository.exceptions.RepositoryException;


public class RepositoryExceptionTest {

	@Test
	public void deveExibirMensagemDoErrorEnum() {
		try {
			throw new RepositoryException(ErrorEnum.ACTIVE_NOT_BOOLEAN);
		} catch (RepositoryException e) {
			assertEquals(ErrorEnum.ACTIVE_NOT_BOOLEAN.getMessage(), e.getMessage());
		}
	}

	@Test
	public void deveExibirMensagemGeral() {
		String message = "Mensagem de erro capturada";
		try {
			throw new RepositoryException(message);
		} catch (RepositoryException e) {
			assertEquals(message, e.getMessage());
		}
	}

}
