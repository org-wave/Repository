package org.wave.repository.validators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wave.repository.enums.ErrorEnum;
import org.wave.repository.enums.MethodEnum;
import org.wave.repository.exceptions.RepositoryException;
import org.wave.repository.validators.ClassValidator;
import org.wave.repository.validators.examples.ClasseEntidade;
import org.wave.repository.validators.examples.ClasseNaoSerializavel;
import org.wave.repository.validators.examples.ClasseSemActive;
import org.wave.repository.validators.examples.ClasseSemActiveBoolean;
import org.wave.repository.validators.examples.ClasseSemAtEntity;
import org.wave.repository.validators.examples.ClasseSemAtId;
import org.wave.repository.validators.examples.ClasseSemAtVersion;
import org.wave.repository.validators.examples.ClasseSemConstrutorPadrao;
import org.wave.repository.validators.examples.ClasseSemEquals;
import org.wave.repository.validators.examples.ClasseSemHashCode;
import org.wave.repository.validators.examples.ClasseSemId;
import org.wave.repository.validators.examples.ClasseSemIdLong;
import org.wave.repository.validators.examples.ClasseSemVersion;
import org.wave.repository.validators.examples.ClasseSemVersionInteger;


public class ClassValidatorTest {

	private ClassValidator validator;

	private Class<?> klass;

	@Before
	public void setUp() {
		this.validator = new ClassValidator();
	}

	@Test(expected = RepositoryException.class)
	public void deveLancarExcecaoQuandoAClasseNaoEstiverAnotadaComAtEntityException() throws RepositoryException {
		this.klass = ClasseSemAtEntity.class;

		this.validator.validate(this.klass);
	}

	@Test
	public void deveLancarExcecaoQuandoAClasseNaoEstiverAnotadaComAtEntity() {
		this.klass = ClasseSemAtEntity.class;

		try {
			this.validator.validate(this.klass);
		} catch (RepositoryException e) {
			assertEquals(ErrorEnum.ANNOTATION_ENTITY_NOT_FOUND.getMessage(this.klass.getName()), e.getMessage());
		}
	}

	@Test(expected = RepositoryException.class)
	public void deveLancarExcecaoQuandoAClasseNaoForSerializavelException() throws RepositoryException {
		this.klass = ClasseNaoSerializavel.class;

		this.validator.validate(this.klass);
	}

	@Test
	public void deveLancarExcecaoQuandoAClasseNaoForSerializavel() {
		this.klass = ClasseNaoSerializavel.class;

		try {
			this.validator.validate(this.klass);
		} catch (RepositoryException e) {
			assertEquals(ErrorEnum.INTERFACE_NOT_FOUND.getMessage(), e.getMessage());
		}
	}

	@Test(expected = RepositoryException.class)
	public void deveLancarExcecaoQuandoAClasseNaoTiverUmAtributoIdException() throws RepositoryException {
		this.klass = ClasseSemId.class;

		this.validator.validate(this.klass);
	}

	@Test
	public void deveLancarExcecaoQuandoAClasseNaoTiverUmAtributoId() {
		this.klass = ClasseSemId.class;

		try {
			this.validator.validate(this.klass);
		} catch (RepositoryException e) {
			assertEquals(ErrorEnum.ID_NOT_FOUND.getMessage(), e.getMessage());
		}
	}

	@Test(expected = RepositoryException.class)
	public void deveLancarUmaExcecaoQuandoOIdDaClasseNaoForDoTipoLongException() throws RepositoryException {
		this.klass = ClasseSemIdLong.class;

		this.validator.validate(this.klass);
	}

	@Test
	public void deveLancarUmaExcecaoQuandoOIdDaClasseNaoForDoTipoLong() {
		this.klass = ClasseSemIdLong.class;

		try {
			this.validator.validate(this.klass);
		} catch (RepositoryException e) {
			assertEquals(ErrorEnum.ID_NOT_LONG.getMessage(), e.getMessage());
		}
	}

	@Test(expected = RepositoryException.class)
	public void deveLancarUmaExcecaoQuandoOIdDaClasseNaoEstiverAnotadoComAtIdException() throws RepositoryException {
		this.klass = ClasseSemAtId.class;

		this.validator.validate(this.klass);
	}

	@Test
	public void deveLancarUmaExcecaoQuandoOIdDaClasseNaoEstiverAnotadoComAtId() {
		this.klass = ClasseSemAtId.class;

		try {
			this.validator.validate(this.klass);
		} catch (RepositoryException e) {
			assertEquals(ErrorEnum.ANNOTATION_ID_NOT_FOUND.getMessage(), e.getMessage());
		}
	}

	@Test(expected = RepositoryException.class)
	public void deveLancarExcecaoQuandoAClasseNaoTiverUmAtributoVersionException() throws RepositoryException {
		this.klass = ClasseSemVersion.class;

		this.validator.validate(this.klass);
	}

	@Test
	public void deveLancarExcecaoQuandoAClasseNaoTiverUmAtributoVersion() {
		this.klass = ClasseSemVersion.class;

		try {
			this.validator.validate(this.klass);
		} catch (RepositoryException e) {
			assertEquals(ErrorEnum.VERSION_NOT_FOUND.getMessage(), e.getMessage());
		}
	}

	@Test(expected = RepositoryException.class)
	public void deveLancarUmaExcecaoQuandoOVersionDaClasseNaoForDoTipoIntegerException() throws RepositoryException {
		this.klass = ClasseSemVersionInteger.class;

		this.validator.validate(this.klass);
	}

	@Test
	public void deveLancarUmaExcecaoQuandoOVersionDaClasseNaoForDoTipoInteger() {
		this.klass = ClasseSemVersionInteger.class;

		try {
			this.validator.validate(this.klass);
		} catch (RepositoryException e) {
			assertEquals(ErrorEnum.VERSION_NOT_INTEGER.getMessage(), e.getMessage());
		}
	}

	@Test(expected = RepositoryException.class)
	public void deveLancarUmaExcecaoQuandoOVersionDaClasseNaoEstiverAnotadoComAtVersionException() throws RepositoryException {
		this.klass = ClasseSemAtVersion.class;

		this.validator.validate(this.klass);
	}

	@Test
	public void deveLancarUmaExcecaoQuandoOVersionDaClasseNaoEstiverAnotadoComAtVersion() {
		this.klass = ClasseSemAtVersion.class;

		try {
			this.validator.validate(this.klass);
		} catch (RepositoryException e) {
			assertEquals(ErrorEnum.ANNOTATION_VERSION_NOT_FOUND.getMessage(), e.getMessage());
		}
	}

	@Test(expected = RepositoryException.class)
	public void deveLancarExcecaoQuandoAClasseNaoTiverUmAtributoActiveException() throws RepositoryException {
		this.klass = ClasseSemActive.class;

		this.validator.validate(this.klass);
	}

	@Test
	public void deveLancarExcecaoQuandoAClasseNaoTiverUmAtributoActive() {
		this.klass = ClasseSemActive.class;

		try {
			this.validator.validate(this.klass);
		} catch (RepositoryException e) {
			assertEquals(ErrorEnum.ACTIVE_NOT_FOUND.getMessage(), e.getMessage());
		}
	}

	@Test(expected = RepositoryException.class)
	public void deveLancarUmaExcecaoQuandoOActiveDaClasseNaoForDoTipoBooleanException() throws RepositoryException {
		this.klass = ClasseSemActiveBoolean.class;

		this.validator.validate(this.klass);
	}

	@Test
	public void deveLancarUmaExcecaoQuandoOActiveDaClasseNaoForDoTipoBoolean() {
		this.klass = ClasseSemActiveBoolean.class;

		try {
			this.validator.validate(this.klass);
		} catch (RepositoryException e) {
			assertEquals(ErrorEnum.ACTIVE_NOT_BOOLEAN.getMessage(), e.getMessage());
		}
	}

	@Test(expected = RepositoryException.class)
	public void deveLancarExcecaoQuandoAClasseNaoTiverConstrutorPadraoException() throws RepositoryException {
		this.klass = ClasseSemConstrutorPadrao.class;

		this.validator.validate(this.klass);
	}

	@Test
	public void deveLancarExcecaoQuandoAClasseNaoTiverConstrutorPadrao() {
		this.klass = ClasseSemConstrutorPadrao.class;

		try {
			this.validator.validate(this.klass);
		} catch (RepositoryException e) {
			assertEquals(ErrorEnum.CONSTRUCTOR_NOT_FOUND.getMessage(this.klass.getName()), e.getMessage());
		}
	}

	@Test(expected = RepositoryException.class)
	public void deveLancarExcecaoQuandoAClasseNaoSobrescreverOMetodoEqualsException() throws RepositoryException {
		this.klass = ClasseSemEquals.class;

		this.validator.validate(this.klass);
	}

	@Test
	public void deveLancarExcecaoQuandoAClasseNaoSobrescreverOMetodoEquals() {
		this.klass = ClasseSemEquals.class;

		try {
			this.validator.validate(this.klass);
		} catch (RepositoryException e) {
			assertEquals(ErrorEnum.METHOD_NOT_FOUND.getMessage(MethodEnum.EQUALS.getValue()), e.getMessage());
		}
	}

	@Test(expected = RepositoryException.class)
	public void deveLancarExcecaoQuandoAClasseNaoSobrescreverOMetodoHashCodeException() throws RepositoryException {
		this.klass = ClasseSemHashCode.class;

		this.validator.validate(this.klass);
	}

	@Test
	public void deveLancarExcecaoQuandoAClasseNaoSobrescreverOMetodoHashCode() {
		this.klass = ClasseSemHashCode.class;

		try {
			this.validator.validate(this.klass);
		} catch (RepositoryException e) {
			assertEquals(ErrorEnum.METHOD_NOT_FOUND.getMessage(MethodEnum.HASHCODE.getValue()), e.getMessage());
		}
	}
	
	@Test
	public void deveRetornarFalsoSeAClasseNaoForUmaEntidade() {
		assertFalse(this.validator.isEntity(ClasseSemHashCode.class));
	}
	
	@Test
	public void deveRetornarVerdadeiroSeAClasseForUmaEntidade() {
		assertTrue(this.validator.isEntity(ClasseEntidade.class));
	}

	@After
	public void tearDown() {
		this.validator = null;
	}

}
