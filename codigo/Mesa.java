/** 
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
 * Classe mesa simples para o restaurante. Uma mesa consegue responder se pode atender uma requisição para um número N de pessoas.
 */
public class Mesa {

	private static int ultimoID;
	private int idMesa;
	private int capacidade;
	private boolean ocupada;

	static{
		ultimoID = 0;
	}

	/**
	 * Cria uma mesa com capacidade mínima de 2 pessoas e id auto-gerado.
	 * @param capacidade Capacidade da mesa. Deve ser maior ou igual a 2.
	 */
	public Mesa(int capacidade) {
		this.capacidade = 2;
		if(capacidade>2)
			this.capacidade = capacidade;
		idMesa = ++ultimoID;
		ocupada = false;
	}

	/**
	 * Sinaliza a mesa como ocupada
	 */
	public void ocupar() {
		ocupada = true;
	}

	
	/**
	 * Sinaliza a mesa como desocupada
	 */
	public void desocupar() {
		ocupada = false;
	}

	/**
	 * Verifica se a mesa pode atender um número determinado de pessoas, citado no parâmetro quantPessoas.
	 * @param quantPessoas Quantidade de pessoas a serem atendidas
	 * @return TRUE/FALSE conforme a mesa pode atender ou não esta quantidade.
	 */
	public boolean estahLiberada(int quantPessoas) {
		return (quantPessoas <= capacidade && !ocupada);
	}


	public int getIdMesa(){
		return idMesa;
	}
	
	public String toString(){
		String descricao = String.format("Mesa %02d (%d pessoas), ",idMesa, capacidade);
		if(ocupada)
			descricao += "ocupada.";
		else 
			descricao += "liberada.";
		
		return descricao;
	}

}
