import { IQuestions } from 'app/shared/model/questions.model';
import { IFormAnswer } from 'app/shared/model/form-answer.model';
import { IStudy } from 'app/shared/model/study.model';

export interface IForms {
  id?: string;
  title?: string | null;
  description?: string | null;
  numResponses?: number | null;
  questions?: IQuestions[] | null;
  formAnswers?: IFormAnswer[] | null;
  study?: IStudy | null;
}

export const defaultValue: Readonly<IForms> = {};
