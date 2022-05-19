import React, { useState } from 'react';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import DeleteIcon from '@mui/icons-material/Delete';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';

interface NginxProps {
  value: string;
  index: number;
  domainValue: string[];
  DelClick: (index: number) => void;
}

export default function DomainBox({
  value,
  index,
  domainValue,
  DelClick,
}: NginxProps) {
  const [domain, setDomain] = useState(value);

  const handleDomainChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setDomain(event.target.value);
    domainValue[index] = event.target.value;
  };

  const handleDelClick = () => {
    DelClick(index);
  };
  return (
    <Grid container spacing={1}>
      <Grid item xs={12} sx={{ margin: 'auto auto' }}>
        <Typography>도메인 URL</Typography>
        <TextField
          fullWidth
          label="Domain URL"
          variant="outlined"
          size="small"
          sx={{ my: 1 }}
          placeholder="http://www.example.com"
          defaultValue={domain}
          onChange={handleDomainChange}
        />
      </Grid>
      <Grid item mb={2}>
        <Button
          variant="outlined"
          startIcon={<DeleteIcon />}
          size="small"
          onClick={handleDelClick}
          sx={{
            color: 'red',
            borderColor: 'red',
          }}
        >
          삭제
        </Button>
      </Grid>
    </Grid>
  );
}
