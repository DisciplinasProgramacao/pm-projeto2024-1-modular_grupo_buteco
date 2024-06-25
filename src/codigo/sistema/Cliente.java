package sistema; /**
 * MIT License
 *
 * Copyright(c) 2024 João Caram <caram@pucminas.br>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

 /**
  * Classe cliente simples para o restaurante. Serve apenas para cadastro com nome e ID
  */
public class Cliente {

	private static int ultimoID;
	private String nomeCliente;
	private int idCliente;

	static{
		ultimoID = 0;
	}

	/**
	 * Cria um cliente com este nome. Nome deve ser maior que 2 caracteres, ou será criado como cliente anônimo.
	 * @param nome Nome do cliente (>2 caracteres)
	 */
	public Cliente(String nome) {
		if(nome.length() > 2)
			nomeCliente = nome;	
		else 
			nomeCliente = "Cliente anônimo";
		idCliente = ++ultimoID;
	}

	@Override
	public String toString(){
		return String.format("Nome: %s - identificador: %d", nomeCliente, idCliente);
	}

	@Override
	public int hashCode(){
		return idCliente;
	}
}
