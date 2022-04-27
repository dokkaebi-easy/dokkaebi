import {
  makeStyles,
  Container,
  Typography,
  TextField,
  Button,
} from '@material-ui/core';
import { useForm } from 'react-hook-form';
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import { useState } from 'react';
import styled from '@emotion/styled';
import { unset } from 'lodash';
import { api } from '../../../api/index';

interface IFormInput {
  principal: string;
  name: string;
  credential: string;
  passwordConfirm: string;
  authKey: string;
}

interface ResponsId {
  name: string;
  state: string;
  principal: string;
}

const schema = yup.object().shape({
  principal: yup
    .string()
    .required('아이디는 필수 입니다.')
    .min(2, '2자 이상 입력하세요.')
    .max(25, '25자 이하로 입력하세요.')
    .matches(/^[a-zA-Z0-9]*$/, '영문과 숫자만 입력 가능합니다.'),
  name: yup
    .string()
    .required('이름은 필수 입니다.')
    .min(2, '2자 이상 입력하세요.')
    .max(25, '25자 이하로 입력하세요.'),
  credential: yup
    .string()
    .required('비밀번호는 필수 입니다.')
    .min(8, '8자 이상 입력하세요.')
    .max(30, '30자 이하로 입력하세요.')
    .matches(
      /^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W))(?=.*[!@#$%^*+=-]).{8,16}$/,
      '비밀번호는 반드시 8~16자이며, 영문, 숫자, 특수문자를 포함해야 합니다.',
    ),
  passwordConfirm: yup
    .string()
    .oneOf([yup.ref('credential'), null], '비밀번호가 일치하지 않습니다.'),
  authKey: yup.string().required('부여받은 인증키를 입력하세요'),
});

const useStyles = makeStyles((theme) => ({
  heading: {
    textAlign: 'center',
    margin: theme.spacing(1, 0, 4),
  },
  submitButton: {
    marginTop: theme.spacing(4),
  },
}));

function SignUp() {
  const [json, setJson] = useState<string>();
  const [inputId, setInputId] = useState<string>('');
  const [inputName, setInputName] = useState<string>('');

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<IFormInput>({
    resolver: yupResolver(schema),
  });

  const { heading, submitButton } = useStyles();

  const onSubmit = (data: IFormInput) => {
    unset(data, 'passwordConfirm');
    singUpAPI(data);
    setJson(JSON.stringify(data));
  };

  const singUpAPI = (sign: any) => {
    api.post(`/user/signup`, sign).then((res) => {
      const data = res.data as ResponsId;
      if (data.state === 'Success') {
        window.location.href = '/login';
      }
    });
    // window.location.href = '/';
  };
  const changeId = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputId(e.currentTarget.value);
  };

  const checkId = (id: string | undefined) => {
    api.post(`/user/duplicate/id?id=${id}`).then((res) => {
      if (res.data.state === 'Fail' || inputId === '') {
        alert('사용할 수 없는 아이디 입니다.');
        setInputId('');
      } else {
        alert('사용 가능한 아이디 입니다.');
      }
    });
  };

  const changeName = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputName(e.currentTarget.value);
  };

  const checkName = (name: string | undefined) => {
    api.post(`/user/duplicate/name?name=${name}`).then((res) => {
      if (res.data.state === 'Fail') {
        alert('사용할 수 없는 이름 입니다.');
        setInputName('');
      } else {
        alert('사용 가능한 이름 입니다.');
      }
    });
  };

  return (
    <Container maxWidth="xs">
      <ContainerDiv>
        <Typography className={heading} variant="h3">
          회원가입
        </Typography>
        <form onSubmit={handleSubmit(onSubmit)} noValidate>
          <TextField
            // eslint-disable-next-line react/jsx-props-no-spreading
            {...register('principal')}
            variant="outlined"
            margin="normal"
            label="ID"
            helperText={errors.principal?.message}
            error={!!errors.principal?.message}
            fullWidth
            InputLabelProps={{ required: false }}
            required
            onChange={changeId}
            value={inputId}
          />
          <Button
            color="primary"
            onClick={() => {
              checkId(inputId);
            }}
          >
            ID 중복 확인
          </Button>
          <TextField
            // eslint-disable-next-line react/jsx-props-no-spreading
            {...register('credential')}
            variant="outlined"
            margin="normal"
            label="비밀번호"
            helperText={errors.credential?.message}
            error={!!errors.credential?.message}
            type="password"
            fullWidth
            InputLabelProps={{ required: false }}
            required
          />
          <TextField
            // eslint-disable-next-line react/jsx-props-no-spreading
            {...register('passwordConfirm')}
            variant="outlined"
            margin="normal"
            label="비밀번호 확인"
            helperText={errors.passwordConfirm?.message}
            error={!!errors.passwordConfirm?.message}
            type="password"
            fullWidth
            InputLabelProps={{ required: false }}
            required
          />
          <TextField
            // eslint-disable-next-line react/jsx-props-no-spreading
            {...register('name')}
            variant="outlined"
            margin="normal"
            label="이름"
            helperText={errors.name?.message}
            error={!!errors.name?.message}
            fullWidth
            InputLabelProps={{ required: false }}
            required
            onChange={changeName}
            value={inputName}
          />
          <Button
            color="primary"
            onClick={() => {
              checkName(inputName);
            }}
          >
            이름 중복 확인
          </Button>
          <TextField
            // eslint-disable-next-line react/jsx-props-no-spreading
            {...register('authKey')}
            variant="outlined"
            margin="normal"
            label="인증키"
            helperText={errors.authKey?.message}
            error={!!errors.authKey?.message}
            fullWidth
            InputLabelProps={{ required: false }}
            required
          />
          <Button
            type="submit"
            fullWidth
            variant="contained"
            color="primary"
            className={submitButton}
          >
            가입하기
          </Button>
        </form>
      </ContainerDiv>
    </Container>
  );
}

export default SignUp;

const ContainerDiv = styled.div`
  width: 400px;
  margin-top: 32px;
  margin-bottom: 32px;
`;
