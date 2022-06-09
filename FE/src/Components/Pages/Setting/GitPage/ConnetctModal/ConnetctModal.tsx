import { useState } from 'react';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Stack from '@mui/material/Stack';
import axios from 'axios';
import { ResponseIdName } from 'Components/MDClass/ResponseIdNameData/ResponseIdNameData';

interface modalSwitch {
  open: boolean;
  Close: () => void;
  Change: (data: ResponseIdName[]) => void;
}

const style = {
  position: 'absolute' as const,
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 600,
  bgcolor: 'background.paper',
  boxShadow: 24,
  borderRadius: 10,
  p: 4,
};

export default function ConnetctModal({ open, Close, Change }: modalSwitch) {
  const [errorId, setErrorId] = useState(false);
  const [errorToken, setErrorToken] = useState(false);
  const [name, setName] = useState('');
  const [apiToken, setApiToken] = useState('');

  const handleIdChange = (event: any) => {
    setName(event.target.value);
  };
  const handleApiTokenChange = (event: any) => {
    setApiToken(event.target.value);
  };

  const handleSaveClick = () => {
    if (!name || !apiToken) {
      if (!name) {
        setErrorId(true);
        setTimeout(() => setErrorId(false), 1000);
      }
      if (!apiToken) {
        setErrorToken(true);
        setTimeout(() => setErrorToken(false), 1000);
      }

      return;
    }

    const parameters = {
      name,
      accessToken: apiToken,
    };
    axios
      .post('/api/git/token', parameters)
      .then((res) => {
        Change(res.data);
        setName('');
        setApiToken('');
        Close();
      })
      .catch(() => {
        // console.log(err);
      });
  };

  const handleCloseClick = () => {
    setName('');
    setApiToken('');
    Close();
  };

  return (
    <Modal
      open={open}
      onClose={Close}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
    >
      <Box sx={style}>
        <Box position="relative" sx={{ top: 20, left: 10 }}>
          <Paper sx={{ padding: 1, textAlign: 'center', width: 250 }}>
            <Typography id="modal-modal-title" variant="h6" component="h2">
              Connect Credentials
            </Typography>
          </Paper>
        </Box>
        <Paper
          sx={{ padding: 3, pt: 4, borderWidth: 3 }}
          elevation={0}
          variant="outlined"
        >
          <Typography
            id="modal-modal-description"
            sx={{ my: 2 }}
            fontFamily="Noto Sans KR"
            fontSize={20}
          >
            GitLab API Token
          </Typography>
          <Typography fontFamily="Noto Sans KR" fontSize={20}>
            토큰 Name
          </Typography>
          <TextField
            fullWidth
            error={errorId}
            helperText={errorId ? '아이디를 적어주세요' : ''}
            label="토큰 Name"
            InputLabelProps={{
              sx: { color: 'rgb(200,200,200)' },
            }}
            variant="outlined"
            size="small"
            sx={{ my: 1 }}
            InputProps={{ sx: { fontWeight: 'bold' } }}
            placeholder="Name"
            onChange={handleIdChange}
          />
          <Typography fontFamily="Noto Sans KR" fontSize={20}>
            API Token
          </Typography>
          <TextField
            fullWidth
            error={errorToken}
            helperText={errorToken ? '토큰을 적어주세요' : ''}
            label="API Token"
            InputLabelProps={{
              sx: { color: 'rgb(200,200,200)' },
            }}
            variant="outlined"
            size="small"
            sx={{ my: 1 }}
            InputProps={{ sx: { fontWeight: 'bold' } }}
            placeholder="API Token"
            onChange={handleApiTokenChange}
          />
          <Stack mt={3} direction="row" justifyContent="flex-end" spacing={2}>
            <Button
              variant="outlined"
              size="small"
              onClick={handleSaveClick}
              sx={{ color: 'black', borderColor: 'black' }}
            >
              저장
            </Button>
            <Button
              variant="outlined"
              size="small"
              onClick={handleCloseClick}
              sx={{ color: 'black', borderColor: 'black' }}
            >
              취소
            </Button>
          </Stack>
        </Paper>
      </Box>
    </Modal>
  );
}
