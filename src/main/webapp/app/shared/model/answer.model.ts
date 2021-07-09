import { IQuestions } from 'app/shared/model/questions.model';
import { IFormAnswer } from 'app/shared/model/form-answer.model';

export interface IAnswer {
  id?: string;
  answer?: string | null;
  questions?: IQuestions | null;
  formAnswer?: IFormAnswer | null;
}

export const defaultValue: Readonly<IAnswer> = {};
